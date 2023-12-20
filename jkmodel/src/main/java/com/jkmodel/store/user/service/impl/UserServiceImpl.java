package com.jkmodel.store.user.service.impl;

import com.jkmodel.store.user.dao.UserDao;
import com.jkmodel.store.user.dto.UserRegisterRequest;
import com.jkmodel.store.user.entity.User;
import com.jkmodel.store.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        //使用MD5加密密碼
        String hashPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashPassword);

        //創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User findUserById(Integer userId) {
        return userDao.findUserById(userId);
    }
}
