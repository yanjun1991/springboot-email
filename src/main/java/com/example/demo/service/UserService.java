package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    User getById(Integer id);
    int insert(User user);
    List<User> getList(int pageNum, int pageSize,String sort);

}
