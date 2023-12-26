package com.jkmodel.store.product.service;


import com.jkmodel.store.photo.dto.Photo;
import com.jkmodel.store.photo.repository.PhotoRepository;
import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.dao.ProductDao;
import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDao productDao;

    @Transactional
    public void saveProductRequest(ProductRequest ProductRequest) {
        System.out.println("name:" + ProductRequest.getName());
        Product product = new Product();
        product.setName(ProductRequest.getName());
        product.setCategory(ProductRequest.getCategory());
        product.setPrice(ProductRequest.getPrice());
        product.setCost(ProductRequest.getCost());
        product.setStock(ProductRequest.getStock());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime onTime = LocalDateTime.parse(ProductRequest.getOnTime(),formatter);
        LocalDateTime offTime = LocalDateTime.parse(ProductRequest.getOffTime(),formatter);
        product.setOnTime(onTime);
        product.setOffTime(offTime);
        //缺admid

        try {
            List<Photo> listP = new ArrayList();
            List<MultipartFile> photoList = (List<MultipartFile>) ProductRequest.getPhotos();
            if(photoList != null){
                for(int i = 0 ; i < photoList.size() ; i++){
                    Photo photo = new Photo();
                    photo.setProduct(product);
                    photo.setPhoto(photoList.get(i).getBytes());
                    listP.add(photo);
                }
                product.setPhotos(listP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(product.getName());
        productRepository.save(product);
        }


    @Override
    public Product update(Integer productNo, Product product) {
        Optional<Product> optionalProduct = productRepository.findById(productNo);

        Product savedProduct = null;

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            product.setProductNo(productNo);
            savedProduct = productRepository.save(product);
        }
        return savedProduct;
    }

    @Override
    public void deletById(Integer productNo) {
        productRepository.deleteById(productNo);
    }

    @Override
    public Product findById(Integer productNo) {
        Product product = productRepository.findById(productNo).orElse(null);
        return product;
    }

    public List<Product> getProducts(ProductQueryParams productQueryParams){
        return productDao.getProducts(productQueryParams);
    }
}
