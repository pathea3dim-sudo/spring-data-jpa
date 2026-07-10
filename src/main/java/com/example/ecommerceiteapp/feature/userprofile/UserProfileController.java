package com.example.ecommerceiteapp.feature.userprofile;


import com.example.ecommerceiteapp.feature.userprofile.dto.UpdateUserProfileRequest;
import com.example.ecommerceiteapp.feature.userprofile.dto.UserProfileResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;


    @PatchMapping("/me")
    public UserProfileResponse updateProfile(
            @RequestBody UpdateUserProfileRequest updateUserProfileRequest
    ) {
        return userProfileService.updateProfile(updateUserProfileRequest);
    }


    @GetMapping("/me")
    public UserProfileResponse me() {
        return userProfileService.me();
    }

}