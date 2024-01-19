package com.jkmodel.store.user.service.impl;


import com.jkmodel.store.user.MailManager;
import com.jkmodel.store.user.dto.LoginRequest;
import com.jkmodel.store.user.dto.UpdateUserRequest;
import com.jkmodel.store.user.entity.User;
import com.jkmodel.store.user.repository.UserRepository;
import com.jkmodel.store.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailManager mailManager;



    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


//    @Override
//    public String loginAndGetJWT(LoginRequest loginRequest) {
//
//        System.out.println("我進到loginAndGetJwt");
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
//
//        System.out.println("回到userService");
//
//        System.out.println(userDetails);
//
//        // Spring Security 將在內部處理身份驗證
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, loginRequest.getPassword(), userDetails.getAuthorities());
//
//        System.out.println(authentication);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // 生成並返回 JWT
//        return jwtTokenProvider.generateToken(authentication);
//    }


    //    @Override
//    public User login(LoginRequest loginRequest) {
//
//        //在進行比對並找出帳號
//        User user = userRepository.findByEmail(loginRequest.getEmail());
//
//        if (user == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//
//        //將登入的密碼加密成MD5
//        String hashPassword = DigestUtils.md5DigestAsHex(loginRequest.getPassword().getBytes());
//
//        //比較密碼
//        if (user.getPassword().equals(hashPassword)){
//            return user;
//        }{
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//
//    }

    @Override
    public User register(User user) {
        //檢查註冊的 email
        User exist = userRepository.findByEmail(user.getEmail()).orElse(null);

        if (exist != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"duplicateEmail");
        }
        //使用MD5 生成密碼的雜湊值
        String hashPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(hashPassword);

        //創建帳號
        User savedUser = userRepository.save(user);

        // 發送註冊確認郵件
        sendRegistrationConfirmationEmail(savedUser.getEmail());

        return savedUser;
    }

    @Override
    public boolean verifyVerificationCode(String userEmail, String enteredCode) {
        String storedCode = stringRedisTemplate.opsForValue().get("verificationCode:" + userEmail);

        // 比對使用者輸入的驗證碼和 Redis 中儲存的驗證碼是否相符
        return storedCode != null && storedCode.equals(enteredCode);
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

    private void sendRegistrationConfirmationEmail(String userEmail) {
        // 自定義郵件內容和參數
        String from = "jimmymomo123@gmail.com"; // 發送者的電子郵件

        List<String> sendTo = Collections.singletonList(userEmail);
        List<String> cc = Collections.emptyList();
        List<String> bcc = Collections.emptyList();
        String personal = "JKModel Shop";
        String subject = "Registration Confirmation";
        String verificationCode = getVerificationCode();
        String context = "Thank you for registering!\n\n" + "驗證碼如下：\n[" + verificationCode + "]"; // 自定義郵件內容

        // 調用注入的 MailManager 的 sentMail 方法
        mailManager.sentMail(from, sendTo, cc, bcc, personal, subject, context);

        System.out.println(userEmail);

        // 將驗證碼儲存在 Redis 中，有效期為一定時間（例如 10 分鐘）
        stringRedisTemplate.opsForValue().set(userEmail, verificationCode, 10, TimeUnit.MINUTES);


    }

    public String getVerificationCode() {

        StringBuilder sb = new StringBuilder();


        // 產生驗證碼
        for (int i = 0; i < 8; i++) {
            switch ((int) (Math.random() * 3)) {
                case 0:
                    sb.append((int) (Math.random() * 10));
                    break;
                case 1:
                    sb.append((char) (int) (Math.random() * 26 + 65));
                    break;
                case 2:
                    sb.append((char) (int) (Math.random() * 26 + 97));
                    break;
            }
        }

        String verificationCode = sb.toString();

        return verificationCode;
    }

}
