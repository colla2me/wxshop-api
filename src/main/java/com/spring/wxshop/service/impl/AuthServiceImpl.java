package com.spring.wxshop.service.impl;

import com.spring.wxshop.service.AuthService;
import com.spring.wxshop.service.SmsCodeService;
import com.spring.wxshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final SmsCodeService smsCodeService;

    static final Pattern TEL_PATTERN = Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");

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
        return code;
    }

    @Override
    public boolean isValidCodeForTel(String tel, String code) {
        return smsCodeService.getCode(tel).equals(code);
    }

    @Override
    public boolean isValidTel(String tel) {
        return TEL_PATTERN.matcher(tel).matches();
    }
}
