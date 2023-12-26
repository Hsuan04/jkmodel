package com.jkmodel.store.admin.controller;


import com.jkmodel.store.admin.dto.Admin;
import com.jkmodel.store.admin.repository.AdminRepository;
import com.jkmodel.store.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/admin")
    public String insert(@RequestBody Admin admin){
        adminService.save(admin);
        return "save admin";
    }

}
