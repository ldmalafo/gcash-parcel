package com.exam.myntexam.controllers;

import com.exam.myntexam.config.RuleManager;
import com.exam.myntexam.config.VoucherConfig;
import com.exam.myntexam.storage.data.ParcelCostRequest;
import com.exam.myntexam.storage.data.ParcelCostResponse;
import com.exam.myntexam.storage.data.ResponseState;
import com.exam.myntexam.storage.data.RuleBank;
import com.exam.myntexam.storage.data.Status;
import com.exam.myntexam.storage.data.VoucherSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lorenzomalafo
 */
@RestController
@RequestMapping("/api")
public class ParcelSettingsController {
    
    @PostMapping(path = "/settings/parcel", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status> updateParcelSettings(@RequestBody RuleBank reqdata) {
        Status valres = validate(reqdata);
        
        if(valres != null) {
            return new ResponseEntity<>(valres, HttpStatus.OK);
        }
        
        RuleManager.getInstance().updateRuleBank(reqdata);
        
        return new ResponseEntity<>(new Status(ResponseState.OKAY.getState(), "Successfully Updated"),
                HttpStatus.OK);
    }
    
    @PostMapping(path = "/settings/voucher", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status> updateVoucherSettings(@RequestBody VoucherSettings reqdata) {
        if(!hasData(reqdata.getUrl())) {
            return new ResponseEntity<>(new Status(ResponseState.ERROR.getState(), 
                    "Missing API URL"), HttpStatus.OK);
        }
        
        if(!hasData(reqdata.getKey())) {
            return new ResponseEntity<>(new Status(ResponseState.ERROR.getState(), 
                    "Missing API Key"), HttpStatus.OK);
        }
        
        VoucherConfig.getInstance().updateVoucherSettings(reqdata.getUrl(), reqdata.getKey());
        
        return new ResponseEntity<>(new Status(ResponseState.OKAY.getState(), "Successfully Updated"),
                HttpStatus.OK);
    }
    
    private Status validate(final RuleBank rule) {
        if(rule.getHeavyWeightPrice() < 0) return new Status(ResponseState.ERROR.getState(), "Price per weight cannot be less than zero.");
        if(rule.getSmallParcelPrice() < 0) return new Status(ResponseState.ERROR.getState(), "Price for Small Parcel cannot be less than zero.");
        if(rule.getMediumParcelPrice() < 0) return new Status(ResponseState.ERROR.getState(), "Price for Medium Parcel cannot be less than zero.");
        if(rule.getLargeParcelPrice() < 0) return new Status(ResponseState.ERROR.getState(), "Price for Large Parcel cannot be less than zero.");
        
        if(rule.getRejectWeight() < 0) return new Status(ResponseState.ERROR.getState(), "Reject weight cannot be less than zero.");
        if(rule.getHeavyWeight() < 0) return new Status(ResponseState.ERROR.getState(), "Heavy weight cannot be less than zero.");
        if(rule.getSmallParcelLimit() < 0) return new Status(ResponseState.ERROR.getState(), "Small Parcel volume cannot be less than zero.");
        if(rule.getMediumParcelLimit() < 0) return new Status(ResponseState.ERROR.getState(), "Medium Parcel volume cannot be less than zero.");
        
        return null;
    }
    
    private boolean hasData(final String strval) {
        return (strval != null && !strval.trim().isEmpty());
    }
}
