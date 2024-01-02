package com.jkmodel.store.admin.service;


import com.jkmodel.store.admin.dto.Admin;

import java.util.Optional;

public interface AdminService {

    Admin save(Admin admin);

    Optional<Admin> findById(Integer adminId);

    Optional<Admin> findByNameAndPassword(String name, String password);

    Iterable<Admin> findAll();

    void deleteById(Integer adminId);


}
