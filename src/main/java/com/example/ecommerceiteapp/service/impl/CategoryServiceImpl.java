package com.example.ecommerceiteapp.service.impl;

import com.example.ecommerceiteapp.domain.Category;
import com.example.ecommerceiteapp.dto.CategoryResponse;
import com.example.ecommerceiteapp.dto.CreateCategoryRequest;
import com.example.ecommerceiteapp.repository.CategoryRepository;
import com.example.ecommerceiteapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createNew(CreateCategoryRequest createCategoryRequest) {
        log.info("createNew {}", createCategoryRequest);

        if (categoryRepository.existsByName(createCategoryRequest.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category has already been used");
        }

        Category parentCategory = null;
        CategoryResponse parentCategoryResponse = null;

        if (createCategoryRequest.parentCategoryId() != null) {
            parentCategory = categoryRepository
                    .findById(createCategoryRequest.parentCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Parent category has not been found"
                    ));

            parentCategoryResponse = CategoryResponse.builder()
                    .id(parentCategory.getId())
                    .name(parentCategory.getName())
                    .description(parentCategory.getDescription())
                    .icon(parentCategory.getIcon())
                    .isDelete(parentCategory.getIsDelete())
                    .build();
        }

        Category category = new Category();
        category.setName(createCategoryRequest.name());
        category.setDescription(createCategoryRequest.description());
        category.setIcon(createCategoryRequest.icon());
        category.setParentCategory(parentCategory);
        category.setIsDelete(false);

        Category savedCategory = categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .description(savedCategory.getDescription())
                .icon(savedCategory.getIcon())
                .isDelete(savedCategory.getIsDelete())
                .parentCategory(parentCategoryResponse)
                .build();
    }
}