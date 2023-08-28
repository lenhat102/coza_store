package com.cybersoft.cozastore.service.imp;

import com.cybersoft.cozastore.payload.response.ProductResponse;

import java.util.List;

public interface ProductServiceImp {

    List<ProductResponse> getProdctByCategory(int id);


    List<ProductResponse> getAllProduct();
}
