package com.example.ecommerceiteapp.feature.category;

import com.example.ecommerceiteapp.feature.category.dto.CategoryResponse;
import com.example.ecommerceiteapp.feature.category.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


    Category categoryRequestToCategory(CreateCategoryRequest categoryRequest);
    Category mapCreateCategoryRequestTotalCaegory(CreateCategoryRequest createCategoryRequest);

    Category toCategory(CreateCategoryRequest request);
    CategoryResponse categoryToCategoryResponse(Category category);




}
