package com.jkmodel.store.admin.service;

import com.jkmodel.store.admin.dto.Admin;
import com.jkmodel.store.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminServiceImpl implements AdminService{


    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin save(Admin admin) {

        Admin savedAdmin = adminRepository.save(admin);
        return savedAdmin;
    }

    @Override
    public Admin findById(Integer adminId) {

//        Admin findByID =
        return null;
    }

    @Override
    public Admin deleteById(Integer adminId) {
        return null;
    }
}
