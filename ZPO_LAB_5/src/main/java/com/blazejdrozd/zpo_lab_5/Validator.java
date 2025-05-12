package com.blazejdrozd.zpo_lab_5;

public interface Validator {
    void validate(String value);
    boolean isValid();
    String getMessage();
}