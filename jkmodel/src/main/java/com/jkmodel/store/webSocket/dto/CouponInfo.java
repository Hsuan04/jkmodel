package com.jkmodel.store.webSocket.dto;

import com.jkmodel.store.coupon.dto.Coupon;

public class CouponInfo {

    Coupon coupon;

    Integer remainingQuantity;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}
