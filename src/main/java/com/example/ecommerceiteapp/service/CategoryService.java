package com.example.ecommerceiteapp.service;


import com.example.ecommerceiteapp.dto.CategoryResponse;
import com.example.ecommerceiteapp.dto.CreateCategoryRequest;

public interface CategoryService {
//    CreateCategoryResponse ;

    CategoryResponse createNew(CreateCategoryRequest createCategoryRequest) throws ReflectiveOperationException;
}
