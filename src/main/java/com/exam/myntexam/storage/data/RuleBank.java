package com.exam.myntexam.storage.data;

/**
 *
 * @author lorenzomalafo
 */
public class RuleBank {
    private long rejectWeight;
    private long heavyWeight;
    private double heavyWeightPrice;
    private long smallParcelLimit;
    private long mediumParcelLimit;
    private double smallParcelPrice;
    private double mediumParcelPrice;
    private double largeParcelPrice;

    public long getRejectWeight() {
        return rejectWeight;
    }

    public void setRejectWeight(long rejectWeight) {
        this.rejectWeight = rejectWeight;
    }

    public long getHeavyWeight() {
        return heavyWeight;
    }

    public void setHeavyWeight(long heavyWeight) {
        this.heavyWeight = heavyWeight;
    }

    public double getHeavyWeightPrice() {
        return heavyWeightPrice;
    }

    public void setHeavyWeightPrice(double heavyWeightPrice) {
        this.heavyWeightPrice = heavyWeightPrice;
    }

    public long getSmallParcelLimit() {
        return smallParcelLimit;
    }

    public void setSmallParcelLimit(long smallParcelLimit) {
        this.smallParcelLimit = smallParcelLimit;
    }

    public long getMediumParcelLimit() {
        return mediumParcelLimit;
    }

    public void setMediumParcelLimit(long mediumParcelLimit) {
        this.mediumParcelLimit = mediumParcelLimit;
    }

    public double getSmallParcelPrice() {
        return smallParcelPrice;
    }

    public void setSmallParcelPrice(double smallParcelPrice) {
        this.smallParcelPrice = smallParcelPrice;
    }

    public double getMediumParcelPrice() {
        return mediumParcelPrice;
    }

    public void setMediumParcelPrice(double mediumParcelPrice) {
        this.mediumParcelPrice = mediumParcelPrice;
    }

    public double getLargeParcelPrice() {
        return largeParcelPrice;
    }

    public void setLargeParcelPrice(double largeParcelPrice) {
        this.largeParcelPrice = largeParcelPrice;
    }
}
