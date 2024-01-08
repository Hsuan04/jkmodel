package com.jkmodel.store.redis.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserViewsRequest implements Serializable {

    private String uuid;

    private String productNo;

//    private String category;
//
//    private Integer views;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public Integer getViews() {
//        return views;
//    }
//
//    public void setViews(Integer views) {
//        this.views = views;
//    }
}
