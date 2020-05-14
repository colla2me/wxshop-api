package com.spring.wxshop.service;

public interface AuthService {

    String sendVerificationCode(String tel);

    boolean isValidCodeForTel(String tel, String code);

    boolean isValidTel(String tel);
}
