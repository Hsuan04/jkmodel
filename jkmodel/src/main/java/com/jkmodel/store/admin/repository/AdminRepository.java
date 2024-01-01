package com.jkmodel.store.admin.repository;

import com.jkmodel.store.admin.dto.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface AdminRepository extends CrudRepository<Admin,Integer> {

    Optional<Admin> findByNameAndPassword(String name, String password);

//    Admin signIn(String name,String password);
}
