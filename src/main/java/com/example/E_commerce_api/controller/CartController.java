package com.example.E_commerce_api.controller;


import com.example.E_commerce_api.model.dto.request.CartItemRequest;
import com.example.E_commerce_api.model.dto.response.CartResponse;
import com.example.E_commerce_api.service.inf.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final ICartService cartService;

    @PostMapping("/items")
    public ResponseEntity<String> addToCart(@Valid @RequestBody CartItemRequest request){
        String response = cartService.addToCart(request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/items")
    public ResponseEntity<CartResponse> updateCart(@Valid @RequestBody CartItemRequest request){
        CartResponse response = cartService.updateCart(request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponse> removeFromCart(@PathVariable Long productId){
        CartResponse response = cartService.removeFromCart(productId);
        return ResponseEntity.ok(response);
    }
    @GetMapping()
    public ResponseEntity<CartResponse> getCart(){
        CartResponse response = cartService.getCart();
        return ResponseEntity.ok(response);
    }
}
