package com.example.E_commerce_api.model.dto.request;

import com.example.E_commerce_api.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotNull(message = "Firstname is required!")
    @Size(min = 5, max = 40, message = "First Name should be between 5 to 40 character")
    private String FirstName;

    @NotNull(message = "Lastname is required!")
    @Size(min = 5, max = 40, message = "Last Name should be between 5 to 40 character")
    private String LastName;

    @Email(message = "Valid Email is required!")
    @NotNull(message = "Email is required!")
    private String email;

    @NotNull(message = "Password is required!")
    @Size(min = 6, message = "password should be minimum 6 characters")
    private String password;

    @NotNull(message = "Role is required!")
    private UserRole role;
}
