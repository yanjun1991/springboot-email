package com.example.demo.service.impl;

import com.example.demo.entity.User;
import org.springframework.stereotype.Service;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.extend.UserExtendMapper;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserExtendMapper userExtendMapper;

    @Override
    public User getById(Integer id) {
        return userExtendMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(User user) {
        return userExtendMapper.insertSelective(user);
    }
}
