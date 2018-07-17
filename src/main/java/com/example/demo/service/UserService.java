package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User getById(Integer id);
    int insert(User user);

}
