package com.jkmodel.store.product.dao;

import com.jkmodel.store.product.QueryParams.ProductQueryParams;
import com.jkmodel.store.product.model.Product;
import com.jkmodel.store.product.rowMapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        //SQL語法
        String sql = "select productNo, `name`, category, price, cost, stock,sellQuantity, buyCount, views, onTime, offTime, lastModifiedTime, photoNo, description, status, admId" +
                "from product where 1=1";
        //where 1=1 sql不會受到影響，方便下面sql拼接

        //將SQL參數傳入Map
        Map<String, Object> map = new HashMap<>();

        //分類：sql加上種類條件
        if(productQueryParams.getCategory() != null){
            sql = sql + " AND category = :category";                 //特別注意 and 之前一定要有一格空白
            map.put("category", productQueryParams.getCategory());   //特別注意 Enum類型要轉成字串
        }

        //查詢條件：sql加上搜尋條件(模糊查詢)
        if(productQueryParams.getSearch() != null){
            sql = sql + " AND name LIKE :name";
            map.put("name", "%" + productQueryParams.getSearch() + "%"); //模糊查詢要加在map裡面，不能寫在sql裡
        }

        //排序：注意！order by 要用字串拼接方式
        sql = sql + " ORDER BY " +  productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        //分頁：限制幾筆資料與跳過幾筆資料
//        sql = sql + " LIMIT :limit OFFSET :offset";
        sql = sql + " LIMIT " + productQueryParams.getLimit() + " OFFSET " + productQueryParams.getOffset();

        map.put("limit", productQueryParams.getLimit());
        map.put("offset", productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }
}
