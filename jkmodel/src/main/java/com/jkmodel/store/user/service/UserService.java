package com.jkmodel.store.user.service;

import com.jkmodel.store.user.dto.LoginRequest;
import com.jkmodel.store.user.dto.UpdateUserRequest;
import com.jkmodel.store.user.dto.UserRegisterRequest;
import com.jkmodel.store.user.entity.User;

public interface UserService {
    User login(LoginRequest loginRequest);
    User register(User user);
    User findUserById(Integer userId);
    User updateUser(Integer userId, UpdateUserRequest updateUserRequest);
    int deleteById(Integer userId);
}
