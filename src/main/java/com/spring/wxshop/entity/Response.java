package com.spring.wxshop.entity;

import org.springframework.http.HttpStatus;

public class Response {

    public static <T> Result<T> success(T data, HttpStatus status) {
        return new Result<>(data, status.value(), status.getReasonPhrase());
    }

    public static <T> Result<T> failure(HttpStatus status, String message) {
        return new Result<>(null, status.value(), message);
    }
}
