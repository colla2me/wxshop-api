package com.spring.wxshop.entity;

import javax.validation.constraints.NotNull;

public class Result<T> {
    T data;
    int code;
    String message;

    public Result(T data, int code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

