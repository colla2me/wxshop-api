package com.spring.wxshop.service.impl;

import com.spring.wxshop.generated.User;

public class UserContext {

    private static final ThreadLocal<User> context = new ThreadLocal<>();

    public static void setContext(User user) {
        context.set(user);
    }

    public static User getContext() {
        return context.get();
    }

    public static void clearContext() {
        context.remove();
    }
}
