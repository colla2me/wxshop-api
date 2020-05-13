package com.spring.wxshop.service;

public interface SmsCodeService {
    String sendSmsCode(String tel);

    void setCode(String code, String tel);

    String getCode(String tel);
}
