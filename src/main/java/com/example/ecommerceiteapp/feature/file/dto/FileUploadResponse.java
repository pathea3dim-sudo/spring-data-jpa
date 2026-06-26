package com.example.ecommerceiteapp.feature.file.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse(
        String name,
        String extension,
        String caption,
        Long size,
        String mediaType,
        String uri
) {
}
