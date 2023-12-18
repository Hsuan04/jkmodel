package com.jkmodel.store.product.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productno")
    private Integer productNo;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Double price;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "sellquantity")
    private Integer sellQuantity;

    @Column(name = "buycount")
    private Integer buyCount;

    @Column(name = "views")
    private Integer views;

    @Column(name = "ontime")
    private Timestamp onTime;

    @Column(name = "offtime")
    private Timestamp offTime;

    @Column(name = "lastmodifiedtime")
    private Timestamp lastModifiedTime;

    @Column(name = "photono")
    private Integer photoNo;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "admid")
    private Integer admId;

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

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

    public Integer getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Integer sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
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

    public Timestamp getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Timestamp lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
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
