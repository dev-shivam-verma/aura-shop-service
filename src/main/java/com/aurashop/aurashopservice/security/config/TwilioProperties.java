package com.aurashop.aurashopservice.security.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties("twilio")
@Data
public class TwilioProperties {
    private String accountSid;
    private String authToken;
    private String messagingServiceSid;
}