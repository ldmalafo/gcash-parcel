package com.exam.myntexam.storage.data;

/**
 *
 * @author lorenzomalafo
 */
public class ParcelCostRequest {
    private Parcel parcel;
    private String voucher;
    private String ignoreExpiration = "NO";

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
    
    public boolean hasParcel() {
        return (parcel != null);
    }

    public String getIgnoreExpiration() {
        return ignoreExpiration;
    }

    public void setIgnoreExpiration(String ignoreExpiration) {
        this.ignoreExpiration = ignoreExpiration;
    }
    
    public boolean hasVoucher() {
        return (voucher != null && !voucher.trim().isEmpty());
    }
}
