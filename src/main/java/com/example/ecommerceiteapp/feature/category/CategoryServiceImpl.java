package com.example.ecommerceiteapp.feature.category;

import com.example.ecommerceiteapp.feature.category.dto.CategoryResponse;
import com.example.ecommerceiteapp.feature.category.dto.CreateCategoryRequest;
import com.example.ecommerceiteapp.feature.category.dto.UpdateCategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        log.info("Creating new category: {}", request);

        // Check if category name already exists
        if (categoryRepository.existsByName(request.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists!");
        }

        // Check if parent category exists (if provided)
        Category parentCategory = null;
        if (request.parentCategoryId() != null) {
            parentCategory = categoryRepository
                    .findById(request.parentCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Parent category not found with id: " + request.parentCategoryId()
                    ));
        }

        // Map request to Category entity
        Category category = categoryMapper.categoryRequestToCategory(request);
        category.setIsDelete(false);
        category.setParentCategory(parentCategory);

        // Save category
        Category savedCategory = categoryRepository.save(category);
        log.info("Category created with id: {}", savedCategory.getId());

        // Return response
        return categoryMapper.categoryToCategoryResponse(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> findCategories(int pageNumber, int pageSize) {
        log.info("Finding categories with page: {}, size: {}", pageNumber, pageSize);

        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortById);

        return categoryRepository
                .findAll(pageRequest)
                .map(categoryMapper::categoryToCategoryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findCategoryById(Integer categoryId) {
        log.info("Finding category by id: {}", categoryId);

        return categoryRepository
                .findById(categoryId)
                .map(categoryMapper::categoryToCategoryResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with id: " + categoryId
                ));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) {
        log.info("Updating category with id: {}, request: {}", id, request);

        // Find existing category
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with id: " + id
                ));

        // Update name if provided
        if (request.name() != null && !request.name().isBlank()) {
            // Check if new name already exists (and it's not the current category)
            if (!request.name().equals(category.getName()) &&
                    categoryRepository.existsByName(request.name())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Category name already exists: " + request.name()
                );
            }
            category.setName(request.name());
        }

        // Update description if provided
        if (request.description() != null) {
            category.setDescription(request.description());
        }

        // Update icon if provided
        if (request.icon() != null) {
            category.setIcon(request.icon());
        }

        // Update isDeleted if provided
        if (request.isDeleted() != null) {
            category.setIsDelete(request.isDeleted());
        }

        // Update parent category if provided
        if (request.parentCategoryId() != null) {
            Category parentCategory = categoryRepository
                    .findById(request.parentCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Parent category not found with id: " + request.parentCategoryId()
                    ));
            category.setParentCategory(parentCategory);
        }

        // Save updated category
        Category updatedCategory = categoryRepository.save(category);
        log.info("Category updated with id: {}", id);

        return categoryMapper.categoryToCategoryResponse(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer categoryId) {
        log.info("Deleting category with id: {}", categoryId);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with id: " + categoryId
                ));

        categoryRepository.delete(category);
        log.info("Category deleted with id: {}", categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponse> allCategories(Integer pageNumber, Integer pageSize) {
        log.info("Fetching all categories with page: {}, size: {}", pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return categoryPage.map(categoryMapper::categoryToCategoryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Integer id) {
        log.info("Getting category by id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with id: " + id
                ));

        return categoryMapper.categoryToCategoryResponse(category);
    }

    @Override
    @Transactional
    public void hardDeleteById(Integer id) {
        log.info("Hard deleting category with id: {}", id);

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
        log.info("Soft deleting category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found with id: " + id
                ));

        category.setIsDelete(true);
        categoryRepository.save(category);
        log.info("Soft deleted category with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getSubCategories(Integer parentCategoryId) {
        log.info("Getting sub-categories for parent id: {}", parentCategoryId);

        // Verify parent category exists
        categoryRepository.findById(parentCategoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Parent category not found with id: " + parentCategoryId
                ));

        // Get sub-categories - you'll need to implement this in repository
        List<Category> subCategories = categoryRepository.findByParentCategoryId(parentCategoryId);

        return subCategories.stream()
                .map(categoryMapper::categoryToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryResponse updateCategoryById(Integer id, UpdateCategoryRequest request) {
        log.info("Updating category by id: {}, request: {}", id, request);

        // This is the same as updateCategory, just delegate to it
        return updateCategory(id, request);
    }
}