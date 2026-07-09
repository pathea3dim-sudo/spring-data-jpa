package com.example.ecommerceiteapp.feature.auth;

import com.example.ecommerceiteapp.feature.auth.dto.RegisterRequest;
import com.example.ecommerceiteapp.feature.auth.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
}
