package com.example.ecommerceiteapp.feature.product.dto;

import com.example.ecommerceiteapp.feature.category.dto.CategorySnippetResponse;

import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        String thumbnail,
        BigDecimal unitPrice,
        Integer qty,
        Boolean isAvailable,
        Boolean isDeleted,
        CategorySnippetResponse categorySnippetResponse

) {
}
