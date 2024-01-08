package com.jkmodel.store.product.service;


import com.jkmodel.store.photo.dto.Photo;
import com.jkmodel.store.photo.repository.PhotoRepository;
import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.dao.ProductDao;
import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Component
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Transactional
    public Product saveProductRequest(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setCost(productRequest.getCost());
        product.setStock(productRequest.getStock());
        product.setDescription(productRequest.getDescription());
        product.setStatus(productRequest.getStatus());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime onTime = LocalDateTime.parse(productRequest.getOnTime(),formatter);
        LocalDateTime offTime = LocalDateTime.parse(productRequest.getOffTime(),formatter);
        product.setOnTime(onTime);
        product.setOffTime(offTime);
        product.setLastModifiedTime(LocalDateTime.now());

        //缺admid

        try {
            List<Photo> listP = new ArrayList();
            List<MultipartFile> photoList = productRequest.getPhotos();
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
        return productRepository.save(product);
        }

    @Override
    public Product updateProductRequest(Integer productNo, ProductRequest productRequest) {

        Optional<Product> optionalProduct = productRepository.findById(productNo);

        photoRepository.deleteAllByProductNo(productNo);

        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(productRequest.getName());
            existingProduct.setCategory(productRequest.getCategory());
            existingProduct.setPrice(productRequest.getPrice());
            existingProduct.setCost(productRequest.getCost());
            existingProduct.setStock(productRequest.getStock());
            existingProduct.setDescription(productRequest.getDescription());
            existingProduct.setStatus(productRequest.getStatus());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime onTime = LocalDateTime.parse(productRequest.getOnTime(),formatter);
            LocalDateTime offTime = LocalDateTime.parse(productRequest.getOffTime(),formatter);
            existingProduct.setOnTime(onTime);
            existingProduct.setOffTime(offTime);
            existingProduct.setLastModifiedTime(LocalDateTime.now());

            try {
                List<Photo> listP = new ArrayList();
                List<MultipartFile> photoList = productRequest.getPhotos();
                if(photoList != null){
                    for(int i = 0 ; i < photoList.size() ; i++){
                        Photo photo = new Photo();
                        photo.setProduct(existingProduct);
                        photo.setPhoto(photoList.get(i).getBytes());
                        listP.add(photo);
                    }
                    existingProduct.setPhotos(listP);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            productRepository.save(existingProduct);
            System.out.println("更新商品與圖片成功");
        }
        return null;
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
        product.setViews(product.getViews() + 1);
        productRepository.save(product);
        return product;
    }

    public Page<Product> getProducts(String search, String category, Double minPrice, Double maxPrice, Pageable pageable) {
        return productRepository.findBySearchAndCategoryAndPriceRange(search, category, minPrice, maxPrice, pageable);
    }
}
