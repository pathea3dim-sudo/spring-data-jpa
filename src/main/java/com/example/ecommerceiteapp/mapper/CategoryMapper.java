package com.example.ecommerceiteapp.mapper;

import com.example.ecommerceiteapp.domain.Category;
import com.example.ecommerceiteapp.dto.CategoryResponse;
import com.example.ecommerceiteapp.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    //return type=target
    //parameter=source

    Category mapCreateCategoryRequestTotalCaegory(CreateCategoryRequest createCategoryRequest);
    CategoryResponse mapCategoryToCategoryResponse (Category category);




}
