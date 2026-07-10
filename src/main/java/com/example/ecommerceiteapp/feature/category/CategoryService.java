package com.example.ecommerceiteapp.feature.category;

import com.example.ecommerceiteapp.feature.category.dto.CategoryResponse;
import com.example.ecommerceiteapp.feature.category.dto.CreateCategoryRequest;
import com.example.ecommerceiteapp.feature.category.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);

    Page<CategoryResponse> findCategories(int pageNumber, int pageSize);

    CategoryResponse findCategoryById(Integer categoryId);

    CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request);

    void deleteCategory(Integer categoryId);

    Page<CategoryResponse> allCategories(Integer pageNumber, Integer pageSize);

    CategoryResponse getCategoryById(Integer id);

    void hardDeleteById(Integer id);

    void softDeleteById(Integer id);

    List<CategoryResponse> getSubCategories(Integer parentCategoryId);

    CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest request);
}