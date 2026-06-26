package com.example.ecommerceiteapp.feature.order.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateOrderRequest (
        @NotBlank(message = "Address is required")
        String address,
        @NotNull(message = "Discount is required")

        @Min(0)
        @Max(100)
        Float discount,
        @Size(max = 255 )
        String remark,

        @NotEmpty(message = "Order line is required")
        List<OrderLineDto> orderLines
){


}
