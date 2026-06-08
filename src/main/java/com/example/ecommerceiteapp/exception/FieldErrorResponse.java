package com.example.ecommerceiteapp.exception;


import lombok.Builder;

@Builder
public record FieldErrorResponse(
        String field,
        String message
) {
}