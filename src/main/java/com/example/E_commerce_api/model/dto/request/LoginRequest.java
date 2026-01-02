package com.example.E_commerce_api.model.dto.request;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequest {

    @Email(message = "valid email is required!")
    @NotBlank(message = "email is required!")
    private String email;

    @NotBlank(message = "Password is required!")
    private String password;

}
