package com.aurashop.aurashopservice.security.dto;


import lombok.Data;

@Data
public class VerificationResult {
    private final String id;
    private final String[] errors;
    private final boolean valid;

    public VerificationResult(String id) {
        this.id = id;
        this.errors = new String[]{};
        this.valid = true;
    }

    public VerificationResult(String[] errors) {
        this.errors = errors;
        this.id = "";
        this.valid = false;
    }
}
