package com.jkmodel.store.photo.dto;

import com.jkmodel.store.product.dto.Product;

import javax.persistence.*;

@Entity
@Table(name="photo")
public class Photo {

    @Id
    @Column(name = "photoNo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer photoNo;

    @ManyToOne
    @JoinColumn(name = "productNo")
    private Product product;

    @Column(name = "photo" , columnDefinition = "longblob")
    private byte[] photo;

    public Integer getPhotoNo() {
        return photoNo;
    }

    public void setPhotoNo(Integer photoNo) {
        this.photoNo = photoNo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
