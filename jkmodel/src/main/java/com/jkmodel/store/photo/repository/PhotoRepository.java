package com.jkmodel.store.photo.repository;

import com.jkmodel.store.photo.dto.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    @Query("SELECT p.photo FROM Photo p WHERE p.product.productNo = :productNo")
    List<Photo> findByProductNo(@Param("productNo") Integer productNo);
}
