package com.example.E_commerce_api.service.inf;

import com.example.E_commerce_api.model.User;
import com.example.E_commerce_api.model.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {

    List<UserResponse> getUsers();
    UserResponse getUserById(Long id);
    User getCurrentUser();

}
