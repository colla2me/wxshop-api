package com.spring.wxshop.entity;

public abstract class Result<T> {
    public enum Status {
        OK("ok"),
        FAIL("fail");
        private final String status;

        Status(String status) {
            this.status = status;
        }
    }

    T data;
    String message;
    Status status;

    public Result(T data, Status status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public String getStatus() {
        return status.status;
    }

    public String getMessage() {
        return message;
    }
}
