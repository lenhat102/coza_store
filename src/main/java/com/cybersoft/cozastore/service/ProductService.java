package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.payload.response.ProductResponse;
import com.cybersoft.cozastore.repository.ProductRepository;
import com.cybersoft.cozastore.service.imp.ProductServiceImp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService implements ProductServiceImp {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<ProductResponse> getProdctByCategory(int id) {

        List<ProductEntity> list = productRepository.findByCategoryId(id);
        List<ProductResponse> responseList = new ArrayList<>();

        for (ProductEntity data : list) {

            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(data.getId());
            productResponse.setName(data.getName());
            productResponse.setImage(data.getImage());
            productResponse.setPrice(data.getPrice());

            responseList.add(productResponse);
        }

        return responseList;
    }

    @Override
    public List<ProductResponse> getAllProduct() {

        List<ProductResponse> responseList = new ArrayList<>();


        List<ProductEntity> list = productRepository.findAll();
        for (ProductEntity data : list) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(data.getId());
            productResponse.setName(data.getName());
            productResponse.setImage(data.getImage());
            productResponse.setPrice(data.getPrice());

            responseList.add(productResponse);
        }

        Gson gson = new Gson();
        String data = gson.toJson(responseList);

//            redisTemplate.opsForValue().set("listProduct",data);


        return responseList;
    }
}
