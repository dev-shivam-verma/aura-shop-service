package com.aurashop.aurashopservice.security.service;

import com.aurashop.aurashopservice.security.dto.CachedOtp;
import com.aurashop.aurashopservice.security.dto.VerificationResult;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SmsOtpPhoneVerificationService implements OtpPhoneVerificationService {

    private static final Integer OTP_CACHE_EXPIRE_MINUTES = 5;
    public static final Integer OTP_REGENERATION_SECONDS = 60;
    private static final String OTP_MESSAGE_TEMPLATE = "Dear Aura Shopper your verification code is: %s. It is valid for %d minutes.";

    private final Cache<String, CachedOtp> otpCache;
    private final SmsService smsService;

    @Autowired
    public SmsOtpPhoneVerificationService(SmsService smsService) {
        this.smsService = smsService;
        otpCache = CacheBuilder.newBuilder().expireAfterWrite(OTP_CACHE_EXPIRE_MINUTES, TimeUnit.MINUTES).build();
    }


    private boolean cacheOtpIfMissingOrExpired(String phone, String otp) {
        CachedOtp cachedOtp = otpCache.getIfPresent(phone);

        if (cachedOtp != null) {
            long now = System.currentTimeMillis();
            long createdAtMillis = cachedOtp.getCreatedAt().getTime();

            if ((now - createdAtMillis) < OTP_REGENERATION_SECONDS * 1000) return false;
        }

        otpCache.put(phone, new CachedOtp(otp, new java.util.Date()));
        return true;
    }

    private String generateRandomOtp() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }


    public void clearOtp(String key) {
        otpCache.invalidate(key);
    }

    @Override
    public VerificationResult sendOtp(String phone) {
        String otp = generateRandomOtp();
        boolean isCached = cacheOtpIfMissingOrExpired(phone, otp);

        if (!isCached) {
            return new VerificationResult(new String[]{"Otp generation requested too soon"});
        }

        String message = OTP_MESSAGE_TEMPLATE.formatted(otp, OTP_CACHE_EXPIRE_MINUTES);
        String smsId = smsService.sendSms(phone, message);


        return new VerificationResult(smsId);
    }

    @Override
    public VerificationResult verifyOtp(String phone, String code) {
        CachedOtp cachedOtp = otpCache.getIfPresent(phone);
        if (cachedOtp == null) return new VerificationResult(new String[]{"No Otp found for this phone number"});
        if (!cachedOtp.getOtp().equals(code)) return new VerificationResult(new String[]{"Otp verification failed", "Invalid code provided"});

        return new VerificationResult( phone + " verified successfully");
    }
}
