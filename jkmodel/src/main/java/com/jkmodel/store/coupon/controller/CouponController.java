package com.jkmodel.store.coupon.controller;

import com.jkmodel.store.coupon.dto.Coupon;
import com.jkmodel.store.coupon.service.CouponService;
import com.jkmodel.store.product.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/addCoupon")
    public ResponseEntity<?> save(@RequestBody @Valid Coupon coupon,
                                  BindingResult bindingResult){

        // 錯誤驗證
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        Coupon savedCoupon = couponService.save(coupon);

        // 成功創建
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCoupon.getCouponNo())
                .toUri();

        return ResponseEntity.created(location).body(savedCoupon);
    }

    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> findAll(){
           List<Coupon> couponList = couponService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(couponList);
        }
    }

