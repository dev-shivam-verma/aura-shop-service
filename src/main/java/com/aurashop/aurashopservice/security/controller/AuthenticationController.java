package com.aurashop.aurashopservice.security.controller;


import com.aurashop.aurashopservice.security.dto.ApiResponse;
import com.aurashop.aurashopservice.security.dto.AuraUserDetail;
import com.aurashop.aurashopservice.security.dto.VerificationResult;
import com.aurashop.aurashopservice.security.entity.Authority;
import com.aurashop.aurashopservice.security.entity.User;
import com.aurashop.aurashopservice.security.service.OtpPhoneVerificationService;
import com.aurashop.aurashopservice.security.service.UserService;
import com.aurashop.aurashopservice.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/public/auth")
public class AuthenticationController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    OtpPhoneVerificationService otpPhoneVerificationService;

    @Autowired
    UserService userService;

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse<?>> sendOtp(@RequestParam String phone) {
        VerificationResult result = otpPhoneVerificationService.sendOtp(phone);

        if (!result.isValid())
            return ResponseEntity.internalServerError().body(ApiResponse.failure("Failed to send OTP: ", Arrays.toString(result.getErrors())));


        return ResponseEntity.ok(ApiResponse.success("OTP sent successfully to " + phone, null));
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestParam String phone, @RequestParam String code) {
        VerificationResult result = otpPhoneVerificationService.verifyOtp(phone, code);

        if (!result.isValid()) {
            return ResponseEntity.badRequest().body(ApiResponse.failure("Failed to verify OTP", Arrays.toString(result.getErrors())));
        }


        User user = userService.findUserByPhoneNumber(phone);
        boolean isNewUser = false;
        if (user == null) {
            user = new User();
            user.setPhoneNumber(phone);

            Authority authority = userService.findAuthorityByName("ROLE_USER");
            user.getAuthorities().add(authority);
            isNewUser = true;
        }


        userService.saveUser(user);
        AuraUserDetail testUserDetail = new AuraUserDetail(user);
        String token = jwtUtil.generateToken(testUserDetail);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("isNewUser", isNewUser);

        return ResponseEntity.status(isNewUser ? HttpStatus.CREATED : HttpStatus.OK).body(ApiResponse.success("OTP verified successfully", data));
    }

}
