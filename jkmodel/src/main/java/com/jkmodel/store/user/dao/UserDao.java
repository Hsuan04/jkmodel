package com.jkmodel.store.user.dao;


import com.jkmodel.store.user.dto.UserRegisterRequest;
import com.jkmodel.store.user.entity.User;

public interface UserDao {

    User findUserById(Integer userId);
    User findUserByEmail(String email);
    Integer createUser(UserRegisterRequest userRegisterRequest);


}
