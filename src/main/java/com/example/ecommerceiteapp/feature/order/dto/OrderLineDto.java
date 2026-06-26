package com.example.ecommerceiteapp.feature.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderLineDto(
        @NotBlank(message = "Code is required")
        String code,

        @Positive
        @NotBlank(message = "Qty is required")
        Integer qty,

        @NotBlank(message = "Unitprice is required")
        @Positive
        BigDecimal unitPrice
) {
}
