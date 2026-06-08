package com.example.ecommerceiteapp.controller;


import com.example.ecommerceiteapp.dto.CategoryResponse;
import com.example.ecommerceiteapp.dto.CreateCategoryRequest;
import com.example.ecommerceiteapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

//    private final CategoryService categoryService;

//    @ResponseStatus

    private  final CategoryService categoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponse createNew(@Valid @RequestBody CreateCategoryRequest categoryRequest) throws ReflectiveOperationException {
        return categoryService.createNew(categoryRequest);
    }


}
