package com.example.ecommerceiteapp.mapper;

import com.example.ecommerceiteapp.domain.Category;
import com.example.ecommerceiteapp.dto.CategoryResponse;
import com.example.ecommerceiteapp.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


    Category mapCreateCategoryRequestTotalCaegory(CreateCategoryRequest createCategoryRequest);

    Category toCategory(CreateCategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);




}
