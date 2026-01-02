package com.example.E_commerce_api.controller;


import com.example.E_commerce_api.model.dto.request.LoginRequest;
import com.example.E_commerce_api.model.dto.request.RegisterRequest;
import com.example.E_commerce_api.model.dto.response.AuthResponse;
import com.example.E_commerce_api.service.inf.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity <String> register(@Valid @RequestBody RegisterRequest request){
     String response= authService.register(request);
     return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity <AuthResponse> login(@Valid @RequestBody LoginRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
