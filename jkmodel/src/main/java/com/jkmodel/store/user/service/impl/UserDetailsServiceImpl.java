package com.jkmodel.store.user.service.impl;

import com.jkmodel.store.user.dto.MyUser;
import com.jkmodel.store.user.entity.User;
import com.jkmodel.store.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElse(null);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),List.of());
    }

}
