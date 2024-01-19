package com.jkmodel.store.user.controller;

import com.jkmodel.store.user.dto.LoginRequest;
import com.jkmodel.store.user.dto.UpdateUserRequest;
import com.jkmodel.store.user.entity.User;
import com.jkmodel.store.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user){

        User saveUser = userService.register(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);

    }

    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {

//        String jwt = userService.loginAndGetJWT(loginRequest);

//        return ResponseEntity.status(HttpStatus.OK).body(jwt);

        return ResponseEntity.status(HttpStatus.OK).body("123");
    }

//    @PostMapping("/users/login")
//    public ResponseEntity<User> login(@RequestBody @Valid LoginRequest loginRequest){
//
//        User user = userService.login(loginRequest);
//
//        return ResponseEntity.status(HttpStatus.OK).body(user);
//    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId,
                                           @RequestBody @Valid UpdateUserRequest updateUserRequest){

        User user = userService.findUserById(userId);

        if (user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //開始修改
        User updateUser = userService.updateUser(userId, updateUserRequest);

        return ResponseEntity.status(HttpStatus.OK).body(updateUser);
    }

}
