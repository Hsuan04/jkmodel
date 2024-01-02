package com.jkmodel.store.product.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductRequest {

    @NotBlank(message = "商品名稱不能為空白")
    private String name;

    private String category;

    @NotNull(message = "商品價格不能為空白")
    @DecimalMin(value = "0.00", message = "商品價格最低為0.00")
    private Double price;

    @NotNull(message = "商品成本不能為空白")
    @DecimalMin(value = "0.00", message = "商品成本最低為0.00")
    private Double cost;

    @NotNull(message = "商品庫存不能為空白")
    @Min(value = 0, message = "商品庫存最低為0")
    private Integer stock;

    private Integer buyCount;

    private Integer views;

    @NotNull(message = "上架時間不能為空白")
    private String onTime;

    @NotNull(message = "下架時間不能為空白")
    private String offTime;

    @NotBlank(message = "商品描述不能為空白")
    private String description;

    private Boolean status;

    private Integer admId;

    private List<MultipartFile> photos;

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

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
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

    public List<MultipartFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MultipartFile> photos) {
        this.photos = photos;
    }
}
