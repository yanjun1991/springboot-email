package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.entity.UserExample;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.extend.UserExtendMapper;

import java.util.List;

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

    @Override
    public List<User> getList(int pageNum, int pageSize,String sort) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize,sort);
        // 获取
        UserExample example = new UserExample();
        example.or();
        List<User> userList = userExtendMapper.selectByExample(example);
        return userList;
    }
}
