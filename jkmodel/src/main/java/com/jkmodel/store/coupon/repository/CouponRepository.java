package com.jkmodel.store.coupon.repository;

import com.jkmodel.store.coupon.dto.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
}
