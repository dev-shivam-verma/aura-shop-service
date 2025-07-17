package com.aurashop.aurashopservice.security.service;

import com.aurashop.aurashopservice.security.dto.VerificationResult;

public interface OtpPhoneVerificationService {
    public VerificationResult sendOtp(String phone);
    public VerificationResult verifyOtp(String phone, String code);
}
