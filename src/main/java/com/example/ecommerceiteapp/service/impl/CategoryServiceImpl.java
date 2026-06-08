package com.example.ecommerceiteapp.service.impl;

import com.example.ecommerceiteapp.domain.Category;
import com.example.ecommerceiteapp.dto.CategoryResponse;
import com.example.ecommerceiteapp.dto.CreateCategoryRequest;
import com.example.ecommerceiteapp.dto.UpdateCategoryRequest;
import com.example.ecommerceiteapp.mapper.CategoryMapper;
import com.example.ecommerceiteapp.repository.CategoryRepository;
import com.example.ecommerceiteapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse createNew(CreateCategoryRequest request) {
        log.info("createNew {}", request);

        if (categoryRepository.existsByNameAndIsDeleteFalse(request.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
        }

        Category parentCategory = null;
        CategoryResponse parentCategoryResponse = null;
        if (request.parentCategoryId() != null) {
            parentCategory = categoryRepository
                    .findByIdAndIsDeleteFalse(request.parentCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Parent category not found"
                    ));

            parentCategoryResponse = CategoryResponse.builder()
                    .id(parentCategory.getId())
                    .name(parentCategory.getName())
                    .description(parentCategory.getDescription())
                    .icon(parentCategory.getIcon())
                    .isDelete(parentCategory.getIsDelete())
                    .build();
        }

        Category category = categoryMapper.toCategory(request);  // fix method name
        category.setIsDelete(false);
        category.setParentCategory(parentCategory);

        Category saved = categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .description(saved.getDescription())
                .icon(saved.getIcon())
                .isDelete(saved.getIsDelete())
                .parentCategory(parentCategoryResponse)
                .build();
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        return createNew(request);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> allCategories(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> categoryPage = categoryRepository.findByIsDeleteFalse(pageable);
        return categoryPage.map(categoryMapper::toCategoryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with id: " + id
                ));
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getSubCategories(Integer parentCategoryId) {
        categoryRepository.findByIdAndIsDeleteFalse(parentCategoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Parent category not found"
                ));

        List<Category> subCategories = categoryRepository.findByParentCategoryIdAndIsDeleteFalse(parentCategoryId);
        return subCategories.stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void hardDeleteById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with id: " + id
                ));
        categoryRepository.delete(category);
        log.info("Hard deleted category with id: {}", id);
    }

    @Override
    @Transactional
    public void softDeleteById(Integer id) {
        Category category = categoryRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with id: " + id
                ));
        category.setIsDelete(true);
        categoryRepository.save(category);
        log.info("Soft deleted category with id: {}", id);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with id: " + id
                ));

        if (request.name() != null && !request.name().isBlank()) {
            if (!request.name().equals(category.getName()) &&
                    categoryRepository.existsByNameAndIsDeleteFalse(request.name())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
            }
            category.setName(request.name());
        }
        if (request.description() != null) {
            category.setDescription(request.description());
        }
        if (request.icon() != null) {
            category.setIcon(request.icon());
        }

        Category updated = categoryRepository.save(category);
        log.info("Updated category with id: {}", id);
        return categoryMapper.toCategoryResponse(updated);
    }
}