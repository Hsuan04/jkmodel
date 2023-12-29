package com.jkmodel.store.photo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jkmodel.store.product.dto.Product;

import javax.persistence.*;
import java.util.Base64;

@Entity
@Table(name="photo")
public class Photo {

    @Id
    @Column(name = "photoNo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photoNo;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "productNo")
    private Product product;

    @JsonBackReference
    @Column(name = "photo" , columnDefinition = "longblob")
    private byte[] photo;

    @Transient
    private String photoString;

    public Integer getPhotoNo() {
        return photoNo;
    }

    public void setPhotoNo(Integer photoNo) {
        this.photoNo = photoNo;
    }

//    public Product getProduct() {
//        return product;
//    }
//
    public void setProduct(Product product) {
        this.product = product;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoString() {
        if (photo != null){
            this.photoString = Base64.getEncoder().encodeToString(photo);
        }
        return photoString;
    }

    public void setPhotoString(String photoString) {
        this.photoString = photoString;
        if (photoString != null) {
            // 將base64轉為byte[]
            this.photo = Base64.getDecoder().decode(photoString);
        }
    }

}
