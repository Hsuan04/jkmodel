package com.jkmodel.store.coupon.service;

import com.jkmodel.store.coupon.dto.Coupon;

import java.util.Optional;

public interface CouponService {

    Coupon save(Coupon coupon);

    Optional<Coupon> findById(Integer couponNo);

}
