package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.payload.response.CategoryResponse;
import com.cybersoft.cozastore.repository.CategoryRepository;
import com.cybersoft.cozastore.service.imp.CategoryServiceImp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService implements CategoryServiceImp {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
//    @Cacheable("listCategory")
    public List<CategoryResponse> getAllCategory() {
        System.out.println("Kiem tra category");
        List<CategoryResponse> responseList = new ArrayList<>();

        if(redisTemplate.hasKey("listCategory")){
            System.out.println("Co gia tri tren redis");
//            Nếu như có thì lấy giá trị lưu trữ trên redis
            String data = redisTemplate.opsForValue().get("listCategory").toString();
            Type listType = new TypeToken<ArrayList<CategoryResponse>>(){}.getType();
            responseList = new Gson().fromJson(data, listType);

        }else{//ko có giá trị thì gọi truy vấn lại
            System.out.println("Khong Co gia tri tren redis");
            List<CategoryEntity> list = categoryRepository.findAll();
            for (CategoryEntity data : list) {
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setId(data.getId());
                categoryResponse.setName(data.getName());

                responseList.add(categoryResponse);
            }

            Gson gson = new Gson();
            String data = gson.toJson(responseList);

            redisTemplate.opsForValue().set("listCategory",data);

        }

        return responseList;
    }
}
