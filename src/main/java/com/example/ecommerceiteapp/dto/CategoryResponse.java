package com.example.ecommerceiteapp.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String description,
        String icon,
        Boolean isDelete,
        CategoryResponse parentCategory
) {
}
