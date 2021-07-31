package com.exam.myntexam.storage.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @author lorenzomalafo
 */
@JsonInclude( value = Include.NON_NULL)
public class Status {
    private String code;
    private String message;
    
    public Status() {}
    
    public Status(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
