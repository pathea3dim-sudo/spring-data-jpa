package com.example.ecommerceiteapp.feature.userprofile.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UpdateUserProfileRequest(
        String firstName,
        String lastName,
        String phoneNumber,
        String gender,
        String address,
        String biography,
        String profilePicture
) {
}