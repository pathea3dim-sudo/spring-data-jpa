package com.example.ecommerceiteapp.feature.file.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse(
        String name,
        String caption,
        Long size,
        String mediaType,
        //http://localhost:9990/file/istad.png
        String uri
) {
}
