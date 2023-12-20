package com.jkmodel.store.user.service;

import com.jkmodel.store.user.dto.UserRegisterRequest;
import com.jkmodel.store.user.entity.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);
    User findUserById(Integer userId);
}
