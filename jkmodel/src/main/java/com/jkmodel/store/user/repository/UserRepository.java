package com.jkmodel.store.user.repository;

import com.jkmodel.store.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
