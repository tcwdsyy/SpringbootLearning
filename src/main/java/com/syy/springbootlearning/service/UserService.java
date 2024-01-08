package com.syy.springbootlearning.service;

import com.syy.springbootlearning.entity.User;
import com.syy.springbootlearning.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Transactional
    public User login(User user) throws SQLException {
        return userMapper.login(user);
    }

    @Transactional
    public void transfer(int fromID, int toID, int amount) {
        userMapper.transfer(-1 * amount, fromID);
        userMapper.transfer(amount, toID);
    }
}
