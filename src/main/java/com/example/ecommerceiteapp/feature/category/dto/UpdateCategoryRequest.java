package com.example.ecommerceiteapp.feature.category.dto;

import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
        @Size(min=2, max=50, message = "name must =be between 2 and 50 characters")
        String name,

        String description,
        String icon
)

{



}
