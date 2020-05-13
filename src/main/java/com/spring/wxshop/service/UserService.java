package com.spring.wxshop.service;

import com.spring.wxshop.generate.User;

public interface UserService {

    User createUserIfNotExists(String tel);
}
