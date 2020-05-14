package com.spring.wxshop.service;

import com.spring.wxshop.generated.User;

public interface UserService {

    User createUserIfNotExists(String tel);

    User getUserByTel(String tel);
}
