package com.example.E_commerce_api.service.impl;

import com.example.E_commerce_api.Exception.ResourceNotFoundException;
import com.example.E_commerce_api.Exception.UnauthorizedException;
import com.example.E_commerce_api.model.User;
import com.example.E_commerce_api.model.UserPrincipal;
import com.example.E_commerce_api.model.dto.response.UserResponse;
import com.example.E_commerce_api.repository.UserRepository;
import com.example.E_commerce_api.service.inf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponse> getUsers() {
        List<UserResponse> userlist;
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());

    }

    @Override
    public UserResponse getUserById(Long id) {
        return mapToUserResponse(userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with Id: "+id)));
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(()-> new UnauthorizedException("user not found"));
    }



    private UserResponse mapToUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .first_name(user.getFirst_name())
                .Last_name(user.getLast_name())
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .build();

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found with email"));
        return new UserPrincipal(user);
    }
}
