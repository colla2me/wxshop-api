package com.spring.wxshop.service.impl;

import com.spring.wxshop.dao.UserDao;
import com.spring.wxshop.generated.User;
import com.spring.wxshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUserIfNotExists(String tel) {
        User user = new User();
        user.setTel(tel);
        try {
            userDao.insertNewUser(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return userDao.getUserByTel(tel);
        }
    }
}
