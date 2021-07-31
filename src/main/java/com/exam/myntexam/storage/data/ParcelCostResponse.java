package com.exam.myntexam.storage.data;

/**
 *
 * @author lorenzomalafo
 */
public class ParcelCostResponse extends Status {
    private ParcelCost cost;

    public ParcelCostResponse() {}
    
    public ParcelCostResponse(String status, String message) {
        super(status, message);
    }

    public ParcelCost getCost() {
        return cost;
    }

    public void setCost(ParcelCost cost) {
        this.cost = cost;
    }
}
