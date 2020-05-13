package com.spring.wxshop.dao;

import com.spring.wxshop.generated.User;
import com.spring.wxshop.generated.UserExample;
import com.spring.wxshop.generated.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDao {
    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public UserDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void insertNewUser(User user) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.insert(user);
        }
    }

    public User getUserByTel(String tel) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            UserExample example = new UserExample();
            example.createCriteria().andTelEqualTo(tel);
            return mapper.selectByExample(example).get(0);
        }
    }
}
