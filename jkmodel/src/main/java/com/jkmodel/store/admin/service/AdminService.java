package com.jkmodel.store.admin.service;


import com.jkmodel.store.admin.dto.Admin;

public interface AdminService {

    Admin save(Admin admin);

    Admin findById(Integer adminId);

    Admin deleteById(Integer adminId);
}
