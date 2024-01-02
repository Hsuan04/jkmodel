package com.jkmodel.store.user.service.impl;

import com.jkmodel.store.user.dao.UserDao;
import com.jkmodel.store.user.dto.LoginRequest;
import com.jkmodel.store.user.dto.UpdateUserRequest;
import com.jkmodel.store.user.entity.User;
import com.jkmodel.store.user.repository.UserRepository;
import com.jkmodel.store.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(LoginRequest loginRequest) {

        //在進行比對並找出帳號
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //將登入的密碼加密成MD5
        String hashPassword = DigestUtils.md5DigestAsHex(loginRequest.getPassword().getBytes());

        //比較密碼
        if (user.getPassword().equals(hashPassword)){
            return user;
        }{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public User register(User user) {
        //檢查註冊的 email
        User exist = userRepository.findByEmail(user.getEmail());

        if (exist != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //使用MD5 生成密碼的雜湊值
        String hashPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(hashPassword);

        //創建帳號
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest updateUserRequest) {

        User user = userRepository.findById(userId).orElse(null);

        user.setName(updateUserRequest.getName());
        user.setEmail(updateUserRequest.getEmail());
        user.setPassword(updateUserRequest.getPassword());
        user.setGender(updateUserRequest.getGender());
        user.setBirthday(updateUserRequest.getBirthday());
        user.setPhone(updateUserRequest.getPhone());
        user.setAddress(updateUserRequest.getAddress());

        return userRepository.save(user);

    }

    @Override
    public int deleteById(Integer userId) {
        //檢查帳號是否存在
        User user = userRepository.findById(userId).orElse(null);

        if (user != null){
            userRepository.deleteById(userId);
            return 1;
        }else {
            return -1;
        }


    }
}
