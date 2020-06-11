package com.spring.wxshop.service.impl;

import com.spring.wxshop.dao.UserDao;
import com.spring.wxshop.generated.User;
import com.spring.wxshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUserIfNotExists(String tel) {
        try {
            User user = new User();
            user.setTel(tel);
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            userDao.insertNewUser(user);
            return user;
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            return userDao.getUserByTel(tel);
        }
    }

    @Override
    public User getUserByTel(String tel) {
        return userDao.getUserByTel(tel);
    }
}
