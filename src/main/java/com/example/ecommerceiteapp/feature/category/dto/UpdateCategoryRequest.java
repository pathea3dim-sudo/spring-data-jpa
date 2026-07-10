package com.example.ecommerceiteapp.feature.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 50, message = "Name must not exceed 50 characters")
        String name,

        String description,

        @Size(max = 255, message = "Icon URL must not exceed 255 characters")
        String icon,

        Boolean isDeleted,

        Integer parentCategoryId  // Add this field
) {}