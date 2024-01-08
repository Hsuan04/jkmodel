package com.jkmodel.store.coupon.service;

import com.jkmodel.store.coupon.dto.Coupon;
import com.jkmodel.store.coupon.repository.CouponRepository;
import com.jkmodel.store.redis.service.CouponRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class CouponServiceImpl implements CouponService{

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponRedisService couponRedisService;

    @Override
    public Coupon save(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        couponRedisService.saveToRedis(savedCoupon);
        return savedCoupon;
    }
}
