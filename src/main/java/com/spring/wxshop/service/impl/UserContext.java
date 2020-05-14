package com.spring.wxshop.service.impl;

import com.spring.wxshop.generated.User;

public class UserContext {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static void setUser(User user) {
        currentUser.set(user);
    }

    public static User getUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
