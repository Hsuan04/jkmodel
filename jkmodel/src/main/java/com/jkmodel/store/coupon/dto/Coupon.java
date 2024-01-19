package com.jkmodel.store.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponNo")
    Integer couponNo;

    @NotBlank(message = "優惠卷名稱不能為空白")
    @Column(name = "name")
    String name;

    @NotNull(message = "折扣金額不能為空白")
    @Min(value = 1, message = "折扣金額最低為1元")
    @Column(name = "discount")
    Integer discount;

    @Column(name = "limitPrice")
    Integer limitPrice;

    @Column(name = "validDays")
    Integer validDays;

    @NotBlank(message = "優惠卷描述不能為空白")
    @Column(name = "description")
    String description;

    @Column(name = "admId")
    Integer admId;

    @Column(name = "startTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime startTime;

    @Column(name = "expireTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime expireTime;

    @Column(name = "releaseTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime releaseTime;

    @Column(name = "quantity")
    Integer quantity;

    public Integer getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(Integer couponNo) {
        this.couponNo = couponNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(Integer limitPrice) {
        this.limitPrice = limitPrice;
    }

    public Integer getValidDays() {
        return validDays;
    }

    public void setValidDays(Integer validDays) {
        this.validDays = validDays;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAdmId() {
        return admId;
    }

    public void setAdmId(Integer admId) {
        this.admId = admId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime != null ? releaseTime : LocalDateTime.now();    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public boolean isCouponValid() {
        // 檢查是否在開始時間之後且在失效日期之前
        LocalDateTime now = LocalDateTime.now();
        return startTime != null && expireTime != null && now.isAfter(startTime) && now.isBefore(expireTime);
    }

}
