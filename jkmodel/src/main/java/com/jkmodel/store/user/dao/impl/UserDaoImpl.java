package com.jkmodel.store.user.dao.impl;

import com.jkmodel.store.user.dao.UserDao;
import com.jkmodel.store.user.dto.UserRegisterRequest;
import com.jkmodel.store.user.entity.User;
import com.jkmodel.store.user.rowMapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User findUserById(Integer userId) {
        String sql = "select userId, name, email, password, gender, birthday, phone, address, createdTime " +
                "from user where userId = :userId";

        Map<String, Object> map = new HashMap<>();

        map.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.size() > 0){
            return userList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        String sql = "select userId, name, email, password, gender, birthday, phone, address, createdTime " +
                "from user where email = :email";

        Map<String, Object> map = new HashMap<>();

        map.put("email", email);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.size() > 0){
            return userList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "insert into user(name, email, password, gender, birthday, phone, address, createdTime) " +
                "values(:name, :email, :password, :gender, :birthday, :phone, :address, :createdTime)";

        Map<String,Object> map = new HashMap<>();

        map.put("name", userRegisterRequest.getName());
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getGender());
        map.put("birthday", userRegisterRequest.getBirthday());
        map.put("phone", userRegisterRequest.getPhone());
        map.put("address", userRegisterRequest.getAddress());
        map.put("createdTime", new Date());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update( sql, new MapSqlParameterSource(map), keyHolder);

        int userId = keyHolder.getKey().intValue();

        return userId;
    }
}
