package com.exam.myntexam.storage.data;

/**
 *
 * @author lorenzomalafo
 */
public class ParcelCost {
    private double subTotal;
    private double discount;
    private double total;
    private long weight;
    private long volume;
    private String category;
    private double priceUsed;
    private String voucher;

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
    

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPriceUsed() {
        return priceUsed;
    }

    public void setPriceUsed(double priceUsed) {
        this.priceUsed = priceUsed;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }
}
