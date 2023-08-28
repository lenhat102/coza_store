package com.cybersoft.cozastore.controller;


import com.cybersoft.cozastore.payload.response.BaseResponse;
import com.cybersoft.cozastore.service.imp.ProductServiceImp;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    private Gson gson =new Gson();



    private Logger logger= LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/clear-cache")
    @CacheEvict(value = "listProduct",allEntries = true)
    public ResponseEntity<?> clearCache(){
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProduct(){

        BaseResponse response=new BaseResponse();
        response.setStatusCode(200);
        response.setData(productServiceImp.getAllProduct());
        logger.info(gson.toJson(response));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<?> getProductByCategory(@PathVariable int id){
        logger.info("Tham so: "+id);

        BaseResponse response=new BaseResponse();
        response.setStatusCode(200);
        response.setData(productServiceImp.getProdctByCategory(id));
        logger.info(gson.toJson(response));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
