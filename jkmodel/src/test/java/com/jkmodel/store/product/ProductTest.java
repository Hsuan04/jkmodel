package com.jkmodel.store.product;


import com.jkmodel.store.photo.dto.Photo;
import com.jkmodel.store.product.dto.Product;
import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.repository.ProductRepository;
import com.jkmodel.store.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    //新增商品
    @Test
    public void addProduct() throws IOException {

        //商品1
        Product product1 = new Product();
        product1.setName("女士半肚 T 恤");
        product1.setCategory("上衣");
        product1.setPrice(1399.00);
        product1.setCost(600.00);
        product1.setStock(200);
        product1.setOnTime(LocalDateTime.now());
        product1.setOffTime(LocalDateTime.now().plusDays(90));
        product1.setLastModifiedTime(LocalDateTime.now());
        product1.setDescription("好看");
        product1.setStatus(true);

        List<Photo> list1 = new ArrayList<>();

        Photo photo1 = new Photo();
        photo1.setProduct(product1);
        Path path1 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/女士半肚 T 恤1.jpeg");
        byte[] photo1byte = Files.readAllBytes(path1);
        photo1.setPhoto(photo1byte);

        Photo photo2 = new Photo();
        photo2.setProduct(product1);
        Path path2 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/女士半肚 T 恤2.jpeg");
        byte[] photo2byte = Files.readAllBytes(path2);
        photo2.setPhoto(photo2byte);

        Photo photo3 = new Photo();
        photo3.setProduct(product1);
        Path path3 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/女士半肚 T 恤3.jpeg");
        byte[] photo3byte = Files.readAllBytes(path3);
        photo3.setPhoto(photo3byte);

        Photo photo4 = new Photo();
        photo4.setProduct(product1);
        Path path4 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/女士半肚 T 恤4.jpeg");
        byte[] photo4byte = Files.readAllBytes(path4);
        photo4.setPhoto(photo4byte);

        list1.add(photo1);
        list1.add(photo2);
        list1.add(photo3);
        list1.add(photo4);

        product1.setPhotos(list1);

        productRepository.save(product1);

        //商品2
        Product product2 = new Product();
        product2.setName("字母 Askin 短T恤");
        product2.setCategory("上衣");
        product2.setPrice(1199.00);
        product2.setCost(400.00);
        product2.setStock(400);
        product2.setOnTime(LocalDateTime.now().plusDays(10));
        product2.setOffTime(LocalDateTime.now().plusDays(90));
        product2.setLastModifiedTime(LocalDateTime.now());
        product2.setDescription("好看");
        product2.setStatus(true);

        List<Photo> list2 = new ArrayList<>();

        Photo photo2_1 = new Photo();
        photo2_1.setProduct(product2);
        Path path2_1 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/字母 Askin 短T恤1.jpeg");
        byte[] photo2_1byte = Files.readAllBytes(path2_1);
        photo2_1.setPhoto(photo2_1byte);

        Photo photo2_2 = new Photo();
        photo2_2.setProduct(product2);
        Path path2_2 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/字母 Askin 短T恤2.jpeg");
        byte[] photo2_2byte = Files.readAllBytes(path2_2);
        photo2_2.setPhoto(photo2_2byte);

        Photo photo2_3 = new Photo();
        photo2_3.setProduct(product2);
        Path path2_3 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/字母 Askin 短T恤3.jpeg");
        byte[] photo2_3byte = Files.readAllBytes(path2_3);
        photo2_3.setPhoto(photo2_3byte);

        Photo photo2_4 = new Photo();
        photo2_4.setProduct(product2);
        Path path2_4 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/字母 Askin 短T恤4.jpeg");
        byte[] photo2_4byte = Files.readAllBytes(path2_4);
        photo2_4.setPhoto(photo2_4byte);

        list2.add(photo2_1);
        list2.add(photo2_2);
        list2.add(photo2_3);
        list2.add(photo2_4);

        product2.setPhotos(list2);

        productRepository.save(product2);

        //商品3
        Product product3 = new Product();
        product3.setName("灰色長袖");
        product3.setCategory("上衣");
        product3.setPrice(899.00);
        product3.setCost(400.00);
        product3.setStock(300);
        product3.setOnTime(LocalDateTime.now().minusDays(20));
        product3.setOffTime(LocalDateTime.now().plusDays(100));
        product3.setLastModifiedTime(LocalDateTime.now());
        product3.setDescription("好看");
        product3.setStatus(true);

        List<Photo> list3 = new ArrayList<>();

        Photo photo3_1 = new Photo();
        photo3_1.setProduct(product3);
        Path path3_1 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/灰色長袖1.jpeg");
        byte[] photo3_1byte = Files.readAllBytes(path3_1);
        photo3_1.setPhoto(photo3_1byte);

        Photo photo3_2 = new Photo();
        photo3_2.setProduct(product3);
        Path path3_2 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/灰色長袖2.jpeg");
        byte[] photo3_2byte = Files.readAllBytes(path3_2);
        photo3_2.setPhoto(photo3_2byte);

        Photo photo3_3 = new Photo();
        photo3_3.setProduct(product3);
        Path path3_3 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/灰色長袖3.jpeg");
        byte[] photo3_3byte = Files.readAllBytes(path3_3);
        photo3_3.setPhoto(photo3_3byte);

        Photo photo3_4 = new Photo();
        photo3_4.setProduct(product3);
        Path path3_4 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/灰色長袖4.jpeg");
        byte[] photo3_4byte = Files.readAllBytes(path3_4);
        photo3_4.setPhoto(photo3_4byte);

        list3.add(photo3_1);
        list3.add(photo3_2);
        list3.add(photo3_3);
        list3.add(photo3_4);

        product3.setPhotos(list3);

        productRepository.save(product3);

        //商品4
        Product product4 = new Product();
        product4.setName("掀蓋後背包");
        product4.setCategory("配件");
        product4.setPrice(2199.00);
        product4.setCost(1200.00);
        product4.setStock(200);
        product4.setOnTime(LocalDateTime.now().minusDays(10));
        product4.setOffTime(LocalDateTime.now().plusDays(110));
        product4.setLastModifiedTime(LocalDateTime.now());
        product4.setDescription("好看");
        product4.setStatus(true);

        List<Photo> list4 = new ArrayList<>();

        Photo photo4_1 = new Photo();
        photo4_1.setProduct(product4);
        Path path4_1 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/掀蓋後背包1.jpeg");
        byte[] photo4_1byte = Files.readAllBytes(path4_1);
        photo4_1.setPhoto(photo4_1byte);

        Photo photo4_2 = new Photo();
        photo4_2.setProduct(product4);
        Path path4_2 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/掀蓋後背包2.jpeg");
        byte[] photo4_2byte = Files.readAllBytes(path4_2);
        photo4_2.setPhoto(photo4_2byte);

        Photo photo4_3 = new Photo();
        photo4_3.setProduct(product4);
        Path path4_3 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/掀蓋後背包3.jpeg");
        byte[] photo4_3byte = Files.readAllBytes(path4_3);
        photo4_3.setPhoto(photo4_3byte);

        Photo photo4_4 = new Photo();
        photo4_4.setProduct(product4);
        Path path4_4 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/掀蓋後背包4.jpeg");
        byte[] photo4_4byte = Files.readAllBytes(path4_4);
        photo4_4.setPhoto(photo4_4byte);

        list4.add(photo4_1);
        list4.add(photo4_2);
        list4.add(photo4_3);
        list4.add(photo4_4);

        product4.setPhotos(list4);

        productRepository.save(product4);

        //商品5
        Product product5 = new Product();
        product5.setName("縮口長褲");
        product5.setCategory("褲子");
        product5.setPrice(1599.00);
        product5.setCost(700.00);
        product5.setStock(300);
        product5.setOnTime(LocalDateTime.now().minusDays(30));
        product5.setOffTime(LocalDateTime.now().plusDays(70));
        product5.setLastModifiedTime(LocalDateTime.now());
        product5.setDescription("好看");
        product5.setStatus(true);

        List<Photo> list5 = new ArrayList<>();

        Photo photo5_1 = new Photo();
        photo5_1.setProduct(product5);
        Path path5_1 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/縮口長褲1.jpeg");
        byte[] photo5_1byte = Files.readAllBytes(path5_1);
        photo5_1.setPhoto(photo5_1byte);

        Photo photo5_2 = new Photo();
        photo5_2.setProduct(product5);
        Path path5_2 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/縮口長褲2.jpeg");
        byte[] photo5_2byte = Files.readAllBytes(path5_2);
        photo5_2.setPhoto(photo5_2byte);

        Photo photo5_3 = new Photo();
        photo5_3.setProduct(product5);
        Path path5_3 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/縮口長褲3.jpeg");
        byte[] photo5_3byte = Files.readAllBytes(path5_3);
        photo5_3.setPhoto(photo5_3byte);

        Photo photo5_4 = new Photo();
        photo5_4.setProduct(product5);
        Path path5_4 = Paths.get("/Users/lawrence/Desktop/Jkmodel測試/縮口長褲4.jpeg");
        byte[] photo5_4byte = Files.readAllBytes(path5_4);
        photo5_4.setPhoto(photo5_4byte);

        list5.add(photo5_1);
        list5.add(photo5_2);
        list5.add(photo5_3);
        list5.add(photo5_4);

        product5.setPhotos(list5);

        productRepository.save(product5);

    }
}
