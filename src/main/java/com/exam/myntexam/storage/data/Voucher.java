package com.exam.myntexam.storage.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author lorenzomalafo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Voucher {
    private String code;
    private Double discount;
    private String expiry;
    private String error;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
