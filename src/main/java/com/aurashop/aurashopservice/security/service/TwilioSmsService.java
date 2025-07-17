package com.aurashop.aurashopservice.security.service;

import com.aurashop.aurashopservice.security.config.TwilioProperties;
import com.aurashop.aurashopservice.security.common.SmsSentStatus;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class TwilioSmsService implements SmsService {


    private final TwilioProperties twilioProperties;


    private final Map<String, SmsSentStatus> smsStatusMap;

    @Autowired
    public TwilioSmsService(TwilioProperties twilioProperties) {
        this.smsStatusMap = new HashMap<>();
        this.twilioProperties = twilioProperties;
    }

    @Override
    public String sendSms(String phone, String message) {
        Message smsMessage = Message.creator(
                new PhoneNumber(phone),
                twilioProperties.getMessagingServiceSid(),
                message
        ).create();

        recordSmsStatus(smsMessage.getSid(), SmsSentStatus.UNDELIVERED);

        return smsMessage.getSid();
    }

    public void recordSmsStatus(String sid, SmsSentStatus smsSentStatus) {
        smsStatusMap.put(sid, smsSentStatus);
    }

    public SmsSentStatus getSmsStatus(String sid) {
        return smsStatusMap.getOrDefault(sid, null);
    }

}
