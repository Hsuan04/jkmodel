package com.jkmodel.store.user.repository;

import com.jkmodel.store.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByEmail(String email);
}
