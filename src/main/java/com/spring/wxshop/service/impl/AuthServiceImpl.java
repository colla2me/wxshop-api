package com.spring.wxshop.service.impl;

import com.spring.wxshop.generate.User;
import com.spring.wxshop.service.AuthService;
import com.spring.wxshop.service.SmsCodeService;
import com.spring.wxshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final SmsCodeService smsCodeService;

    @Autowired
    public AuthServiceImpl(UserService userService, SmsCodeService smsCodeService) {
        this.userService = userService;
        this.smsCodeService = smsCodeService;
    }

    @Override
    public String sendVerificationCode(String tel) {
        userService.createUserIfNotExists(tel);
        String code = smsCodeService.sendSmsCode(tel);
        smsCodeService.setCode(code, tel);
        return null;
    }
}
