package com.jkmodel.store.user.service;

import com.jkmodel.store.user.dto.LoginRequest;
import com.jkmodel.store.user.dto.UpdateUserRequest;
import com.jkmodel.store.user.entity.User;

public interface UserService {
//    User login(LoginRequest loginRequest);
//    String loginAndGetJWT(LoginRequest loginRequest);
    User register(User user);
    boolean verifyVerificationCode(String userEmail, String enteredCode);
    User findUserById(Integer userId);
    User updateUser(Integer userId, UpdateUserRequest updateUserRequest);
    int deleteById(Integer userId);
}
