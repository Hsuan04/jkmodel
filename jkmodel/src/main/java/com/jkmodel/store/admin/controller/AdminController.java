package com.jkmodel.store.admin.controller;


import com.jkmodel.store.admin.dto.Admin;
import com.jkmodel.store.admin.dto.LogIn;
import com.jkmodel.store.admin.service.AdminService;
import com.jkmodel.store.product.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;


    @PostMapping("/insert")
    public ResponseEntity<Admin> insert(@RequestBody Admin admin) {
        Admin createdAdmin = adminService.save(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }

    @PutMapping("/admin/{adminId}")
    public ResponseEntity<Admin> update(@PathVariable Integer adminId,
                                        @RequestBody Admin admin) {
        //先搜尋有無此筆資料
        Admin updateAdmin = adminService.findById(adminId).orElse(null);
        //如果不等於null則存入新資料
        if (updateAdmin != null) {
            updateAdmin.setName(admin.getName());
            adminService.save(updateAdmin);
            return ResponseEntity.status(HttpStatus.OK).body(updateAdmin);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/admin/{adminId}")
        public ResponseEntity<Void> delete(@PathVariable Integer adminId) {
            adminService.deleteById(adminId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/read/{adminId}")
        public ResponseEntity<Admin> read(@PathVariable Integer adminId) {
            Admin readadmin = adminService.findById(adminId).orElse(null);
            return ResponseEntity.status(HttpStatus.OK).body(readadmin);
    }


@GetMapping("/findAll")
public ResponseEntity<Iterable<Admin>> findAll() {
    Iterable<Admin> findalladmin = adminService.findAll();
    if (findalladmin != null) {
        return ResponseEntity.status(HttpStatus.OK).body(findalladmin);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
    

    @PostMapping("/logIn")
    public ResponseEntity<Admin> logIn(@RequestBody LogIn user) {
        String name = user.getName();
        String pass = user.getPassword();

      Admin logInAmin = adminService.findByNameAndPassword(name, pass).orElse(null);
//        Admin logInAmin = adminService.findById(1).orElse(null);

        // 比對密碼
        if (logInAmin.getPassword().equals(pass)) {
            // 密碼相符，可以執行其他邏輯
            return ResponseEntity.status(HttpStatus.OK).body(logInAmin);
        } else {
            // 找不到使用者
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
