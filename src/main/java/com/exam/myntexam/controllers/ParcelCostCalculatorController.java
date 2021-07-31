package com.exam.myntexam.controllers;

import com.exam.myntexam.config.VoucherConfig;
import com.exam.myntexam.storage.data.Parcel;
import com.exam.myntexam.storage.data.ParcelCost;
import com.exam.myntexam.storage.data.ParcelCostRequest;
import com.exam.myntexam.storage.data.ParcelCostResponse;
import com.exam.myntexam.storage.data.ResponseState;
import com.exam.myntexam.config.RuleManager;
import com.exam.myntexam.storage.data.Voucher;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author lorenzomalafo
 */
@RestController
@RequestMapping("/api")
public class ParcelCostCalculatorController {
    
    @PostMapping(path = "/parcel", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParcelCostResponse> calculateParcelCost(@RequestBody ParcelCostRequest pcrequest) {
        if(pcrequest.hasParcel()) {
            Optional<ParcelCostResponse> valResp = validate(pcrequest.getParcel());
            
            if(valResp.isPresent()) {
                return new ResponseEntity<>(valResp.get(), HttpStatus.OK);
            }
            
            ParcelCostResponse cost = applyRules(pcrequest.getParcel());
            
            if(pcrequest.hasVoucher() && !ResponseState.REJECT.getState().equalsIgnoreCase(cost.getCode())) {
                applyVoucher(cost, pcrequest.getVoucher(), "YES".equalsIgnoreCase(pcrequest.getIgnoreExpiration()));
            }
            
            return new ResponseEntity<>(cost, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(new ParcelCostResponse(ResponseState.ERROR.getState(), "Parcel Data Needed"),
                HttpStatus.OK);
    }
    
    private Optional<ParcelCostResponse> validate(final Parcel parcel) {
        if(parcel.getLength() > 100_000)
            return Optional.of(new ParcelCostResponse(ResponseState.ERROR.getState(), 
                    "Length too large. Use less than 100,000."));
        
        if(parcel.getWidth() > 100_000) 
            return Optional.of(new ParcelCostResponse(ResponseState.ERROR.getState(), 
                    "Width too large. Use less than 100,000."));
        
        if(parcel.getHeight() > 100_000) 
            return Optional.of(new ParcelCostResponse(ResponseState.ERROR.getState(), 
                    "Height too large. Use less than 100,000."));
        
        return Optional.empty();
    }
    
    private ParcelCostResponse applyRules(final Parcel parcel) {
        Optional<ParcelCost> calcRes = RuleManager.getInstance().calculateCost(parcel);
        
        ParcelCostResponse r = new ParcelCostResponse();
        
        if(calcRes.isEmpty()) {
            r.setCode(ResponseState.ERROR.getState());
            r.setMessage("Failed to calculate. No Parcel Data.");
        } else {
            r.setCost(calcRes.get());
            
            if("Reject".equalsIgnoreCase(r.getCost().getCategory())) {
                r.setCode(ResponseState.REJECT.getState());
                r.setMessage("Parcel exceeds weight limit.");
            } else {
                r.setCode(ResponseState.OKAY.getState());
            }
        }
        
        return r;
    }
    
    private void applyVoucher(final ParcelCostResponse cost, final String voucher, boolean ignoreVoucherExpire) {
        if(voucher == null || voucher.trim().isEmpty()) return;
        
        String verifyUrl = VoucherConfig.getInstance().constructVoucherUrl(voucher.trim());
        
        RestTemplate template = new RestTemplate();
        
        try {
            Voucher voucherData = template.getForObject(verifyUrl, Voucher.class);
            
            if(voucherData != null) {
                if(!ignoreVoucherExpire && isExpiredVoucher(voucherData)) {
                    cost.setMessage("Expired Voucher - " + voucher + ", on " + voucherData.getExpiry() + ".");
                } else {
                    applyDiscountIfValid(cost.getCost(), voucherData);
                }
            } else {
                cost.setMessage("Invalid Voucher Data from Provider Voucher URL.");
            }
        } catch(RestClientException rce) {
            Logger.getLogger(getClass().getName())
                    .log(Level.SEVERE, "Failed to Read from Voucher Rest", rce);
            
            cost.setMessage("Failed to Validate Voucher Code. " + rce.getMessage());
        }
    }
    
    private boolean isExpiredVoucher(final Voucher vdata) {
        boolean expired = false;
        
        if(vdata.getExpiry() != null && !vdata.getExpiry().trim().isEmpty()) {
            LocalDate exp = LocalDate.parse(vdata.getExpiry().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            
            if(LocalDate.now().isAfter(exp)) {
                expired = true;
            }
        }
        
        return expired;
    }
        
    private void applyDiscountIfValid(final ParcelCost pcost, final Voucher vdata) {
        pcost.setVoucher(vdata.getCode());
        
        if(vdata.getDiscount() == null) return;
        
        pcost.setTotal(pcost.getSubTotal() - vdata.getDiscount());
        pcost.setDiscount(vdata.getDiscount());
    }
}
