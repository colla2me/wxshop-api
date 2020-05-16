package com.spring.wxshop.service;

public interface AuthService {

    String sendVerificationCode(String tel);

    boolean isCorrectCode(String tel, String code);

    boolean isValidTel(String tel);
}
