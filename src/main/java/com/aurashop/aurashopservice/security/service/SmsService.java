package com.aurashop.aurashopservice.security.service;

import com.aurashop.aurashopservice.security.common.SmsSentStatus;

public interface SmsService {
    public String sendSms(String phone, String message);
    public SmsSentStatus getSmsStatus(String phone);
}
