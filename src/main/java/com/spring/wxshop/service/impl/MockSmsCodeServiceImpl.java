package com.spring.wxshop.service.impl;

import com.spring.wxshop.service.SmsCodeService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MockSmsCodeServiceImpl implements SmsCodeService {
    private final Map<String, String> telAndCodes = new ConcurrentHashMap<>();

    @Override
    public String sendSmsCode(String tel) {
        return "000000";
    }

    @Override
    public void setCode(String code, String tel) {
        telAndCodes.put(tel, code);
    }

    @Override
    public String getCode(String tel) {
        return telAndCodes.get(tel);
    }
}
