package com.test.springbootdemo.core.dto;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GenericDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status = StringUtils.EMPTY;
    private Map<String, String> errors = new HashMap<>();
    private Map<String, Object> data = new HashMap<>();

    public GenericDto() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void addError(String key, String value) {
        this.errors.put(key, value);
    }

    public static GenericDto newFailureGenericDto() {
        GenericDto genericDto = new GenericDto();
        genericDto.setStatus("Failure");
        return genericDto;
    }

    public static GenericDto newSuccessGenericDto() {
        GenericDto genericDto = new GenericDto();
        genericDto.setStatus("Success");
        return genericDto;
    }

}
