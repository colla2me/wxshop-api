package com.spring.wxshop.entity;

import com.spring.wxshop.generated.User;

public class LoginStatus {
    private boolean login;
    private User user;

    public static LoginStatus login(User user) {
        return new LoginStatus(user, true);
    }

    public static LoginStatus logout(int code) {
        return new LoginStatus(null, false);
    }

    public LoginStatus() {}

    public LoginStatus(User user, boolean login) {
        this.user = user;
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }

    public User getUser() {
        return user;
    }
}
