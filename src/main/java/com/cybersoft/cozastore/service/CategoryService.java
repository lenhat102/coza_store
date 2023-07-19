package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.payload.response.CategoryResponse;
import com.cybersoft.cozastore.repository.CategoryRepository;
import com.cybersoft.cozastore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService implements CategoryServiceImp {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Cacheable("listCategory")
    public List<CategoryResponse> getAllCategory() {
        System.out.println("Kiem tra category");
        List<CategoryEntity> list=categoryRepository.findAll();

        List<CategoryResponse> responseList=new ArrayList<>();

        for (CategoryEntity data: list) {
            CategoryResponse categoryResponse=new CategoryResponse();
            categoryResponse.setId(data.getId());
            categoryResponse.setName(data.getName());

            responseList.add(categoryResponse);
            
        }

        return responseList;
    }
}
