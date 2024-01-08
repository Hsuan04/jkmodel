package com.jkmodel.store.product.repository;

import com.jkmodel.store.product.dto.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Override
    Optional<Product> findById(Integer productNo);

    @Query("SELECT p FROM Product p WHERE " +
            "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:category IS NULL OR p.category = :category) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> findBySearchAndCategoryAndPriceRange(String search, String category, Double minPrice, Double maxPrice, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category = :category ORDER BY p.views DESC")
    List<Product> findTopProductsByCategory(String category, Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.views DESC")
    List<Product> findTopProducts(Pageable pageable);

}
