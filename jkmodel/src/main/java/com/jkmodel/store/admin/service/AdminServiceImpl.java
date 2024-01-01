package com.jkmodel.store.admin.service;

import com.jkmodel.store.admin.dto.Admin;
import com.jkmodel.store.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public Optional<Admin> findById(Integer adminId) {
        return adminRepository.findById(adminId);
    }

    @Override
    public Optional<Admin> findByNameAndPassword(String name, String password) {
        return adminRepository.findByNameAndPassword(name, password);
    }

    @Override
    public void deleteById(Integer adminId) {
        adminRepository.deleteById(adminId);

    }
}
