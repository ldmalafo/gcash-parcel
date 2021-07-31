package com.exam.myntexam.storage.data;

/**
 *
 * @author lorenzomalafo
 */
public enum ResponseState {
    OKAY("OK"),
    ERROR("ERROR"),
    REJECT("REJECT");
    
    private final String state;
    private ResponseState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }
}
