package com.example.E_commerce_api.service.impl;

import com.example.E_commerce_api.Exception.BadRequestException;
import com.example.E_commerce_api.model.Cart;
import com.example.E_commerce_api.model.User;
import com.example.E_commerce_api.model.Wishlist;
import com.example.E_commerce_api.model.dto.request.LoginRequest;
import com.example.E_commerce_api.model.dto.request.RegisterRequest;
import com.example.E_commerce_api.model.dto.response.AuthResponse;
import com.example.E_commerce_api.model.enums.UserRole;
import com.example.E_commerce_api.repository.CartRepository;
import com.example.E_commerce_api.repository.UserRepository;
import com.example.E_commerce_api.repository.WishlistRepository;
import com.example.E_commerce_api.service.inf.IAuthService;
import com.example.E_commerce_api.service.inf.IJwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final WishlistRepository wishlistRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    private final AuthenticationManager authenticationManager;
    private final IJwtService jwtService ;

    @Override
    @Transactional
    public String register(RegisterRequest request) {
       if(userRepository.existsByEmail(request.getEmail()))
           throw new BadRequestException("Email already exits");

       User user = User.builder()
               .first_name(request.getFirstName())
               .last_name(request.getLastName())
               .email(request.getEmail())
               .password(encoder.encode(request.getPassword()))
               .role(request.getRole())
               .build();
      User savedUser=userRepository.save(user);

      if(request.getRole().equals(UserRole.USER.toString())){
          Cart cart = Cart.builder()
                  .user(savedUser)
                  .build();
          cartRepository.save(cart);

          Wishlist wishlist = Wishlist.builder()
                  .user(savedUser)
                  .build();
          wishlistRepository.save(wishlist);
      }
       return "User created Successfully!";
    }

    @Override
    public AuthResponse login(LoginRequest request) {
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(()->new BadRequestException("Invalid Email or password!"));
//
//        if(!user.getPassword().equals(request.getPassword()))
//            throw new BadRequestException("Invalid Email or Password!");
//
//        return AuthResponse.builder()
//               .token("123")
//                .build();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        if(authentication.isAuthenticated()) {
            return  AuthResponse.builder()
                        .token(jwtService.generateToken(request.getEmail()))
                        .build();


        }
        return AuthResponse.builder()
                .token("")
                .build();

    }
}
