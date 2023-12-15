package com.jkmodel.store.product.dto;

import java.sql.Timestamp;

public class ProductRequest {

//    private Integer productNo;
    private String name;
    private String category;
    private Double price;
    private Double cost;
    private Integer stock;
//    private Integer sellQuantity;
//    private Integer buyCount;
//    private Integer views;
    private Timestamp onTime;
    private Timestamp offTime;
//    private Timestamp lastModifiedTime;
    private Integer photoNo;
    private String description;
    private Boolean status;
    private Integer admId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Timestamp getOnTime() {
        return onTime;
    }

    public void setOnTime(Timestamp onTime) {
        this.onTime = onTime;
    }

    public Timestamp getOffTime() {
        return offTime;
    }

    public void setOffTime(Timestamp offTime) {
        this.offTime = offTime;
    }

    public Integer getPhotoNo() {
        return photoNo;
    }

    public void setPhotoNo(Integer photoNo) {
        this.photoNo = photoNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getAdmId() {
        return admId;
    }

    public void setAdmId(Integer admId) {
        this.admId = admId;
    }
}
