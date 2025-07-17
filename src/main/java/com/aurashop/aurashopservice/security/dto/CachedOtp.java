package com.aurashop.aurashopservice.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CachedOtp {
    private String otp;
    private Date createdAt;
}
