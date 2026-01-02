package com.example.E_commerce_api.service.inf;

import com.example.E_commerce_api.model.dto.request.LoginRequest;
import com.example.E_commerce_api.model.dto.request.RegisterRequest;
import com.example.E_commerce_api.model.dto.response.AuthResponse;

public interface IAuthService {
    String register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
