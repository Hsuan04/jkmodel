package com.jkmodel.store.product.dao;

import com.jkmodel.store.product.dto.ProductRequest;
import com.jkmodel.store.product.model.Product;
import com.jkmodel.store.product.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getByProductNo(Integer productNo) {

        String sql = "SELECT productNo, name, category, price, cost, stock, sellQuantity, buyCount, views," +
                     "onTime, offTime, lastModifiedTime, photoNo, `description`, status, admId" +
                     " FROM product WHERE productNo = :productNo";
        Map<String , Object> map = new HashMap();
        map.put("productNo",productNo);
        List<Product> productList = namedParameterJdbcTemplate.query(sql,map, new ProductRowMapper());

        if(productList.size() > 0){
            return productList.get(0);
        } else {
            return null;
        }

    }

    @Override
    public Integer insert(ProductRequest productRequest) {
        String sql = "insert into product(name, category, price, cost, stock, onTime, offTime, lastModifiedTime, photoNo, description, status, admId) value(name, category, price, cost, stock, onTime, offTime, lastModifiedTime, photoNo, description, status, admId)";

        Map<String, Object> map = new HashMap<>();
        map.put("name", productRequest.getName());
        map.put("category", productRequest.getCategory());
        map.put("price", productRequest.getPrice());
        map.put("cost", productRequest.getCost());
        map.put("stock", productRequest.getStock());
        map.put("onTime", productRequest.getOnTime());
        map.put("offTime", productRequest.getOffTime());
        map.put("photoNo", productRequest.getPhotoNo());
        map.put("description", productRequest.getDescription());
        map.put("status", productRequest.getStatus());
        map.put("admId", productRequest.getAdmId());

        Date now = new Date();
        map.put("lastModifiedTime", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int productNo = keyHolder.getKey().intValue();

//        namedParameterJdbcTemplate.query(sql, map, new MapSqlParameterSource(map), keyHolder);

        return productNo;

    }

}
