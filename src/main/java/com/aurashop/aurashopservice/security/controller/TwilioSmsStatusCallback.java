package com.aurashop.aurashopservice.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/twilio/sms-callback")
public class TwilioSmsStatusCallback {

    @PostMapping
    public ResponseEntity<?> handleSmsStatusCallback(
            @RequestParam("MessageSid") String messageSid,
            @RequestParam("MessageStatus") String messageStatus,
            @RequestParam("ErrorCode    ") String errorCode
    ){
        System.out.println("Received SMS status callback: " +
                "messageSid=" + messageSid +
                "messageStatus=" + messageStatus +
                ", errorCode=" + errorCode
        );

        return ResponseEntity.ok().build();
    }
}
