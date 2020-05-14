package com.spring.wxshop.entity;

import com.spring.wxshop.generated.User;

public class LoginResult extends Result<User> {

    private final boolean isLogin;

    public static LoginResult notLoggedIn() {
        return new LoginResult(false, null, Status.OK);
    }

    public static LoginResult loggedIn(User user) {
        return new LoginResult(true, user, Status.OK);
    }

    public LoginResult(boolean isLogin, User user, Status status) {
        super(user, status,"");
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
