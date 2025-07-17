package com.aurashop.aurashopservice.security.config;


import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {


    @Autowired
    public TwilioInitializer(TwilioProperties twilioProperties) {
        Twilio.init(twilioProperties.getAccountSid(), twilioProperties.getAuthToken());
        System.out.println("Twilio initialized with account-" + twilioProperties.getAccountSid());
    }
}