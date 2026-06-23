package com.example.ecommerceiteapp.feature.category;

import com.example.ecommerceiteapp.feature.category.dto.CategoryResponse;
import com.example.ecommerceiteapp.feature.category.dto.CreateCategoryRequest;
import com.example.ecommerceiteapp.feature.category.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;
import java.util.List;

public interface CategoryService {

    Page<CategoryResponse> allCategories(Integer pageNumber, Integer pageSize);

    CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest);

    CategoryResponse getCategoryById(Integer id);

    void hardDeleteById(Integer id);

    void softDeleteById(Integer id);

    List<CategoryResponse> getSubCategories(Integer parentCategoryId);

    CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest updateCategoryRequest);
}