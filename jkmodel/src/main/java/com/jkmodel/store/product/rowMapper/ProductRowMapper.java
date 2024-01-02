package com.jkmodel.store.product.rowMapper;

import com.jkmodel.store.product.dto.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setProductNo(resultSet.getInt("productNo"));
        product.setName(resultSet.getString("name"));
        product.setCategory(resultSet.getString("category"));
        product.setPrice(resultSet.getDouble("price"));
        product.setCost(resultSet.getDouble("cost"));
        product.setStock(resultSet.getInt("stock"));
        product.setSellQuantity(resultSet.getInt("sellQuantity"));
        product.setBuyCount(resultSet.getInt("buyCount"));
        product.setViews(resultSet.getInt("views"));
        product.setOnTime(resultSet.getTimestamp("onTime").toLocalDateTime());
        product.setOffTime(resultSet.getTimestamp("offTime").toLocalDateTime());
        product.setLastModifiedTime(resultSet.getTimestamp("lastModifiedTime").toLocalDateTime());
        product.setDescription(resultSet.getString("description"));
        product.setStatus(resultSet.getBoolean("status"));
        product.setAdmId(resultSet.getInt("admId"));

        byte[] photoBytes = resultSet.getBytes("photo");
        if (photoBytes != null) {
            product.setFirstPhotoByte(photoBytes);
            product.setFirstPhotoString(Base64.getEncoder().encodeToString(photoBytes));
        }
        return product;
    }
}
