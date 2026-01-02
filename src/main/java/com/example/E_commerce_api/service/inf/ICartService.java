package com.example.E_commerce_api.service.inf;

import com.example.E_commerce_api.model.dto.request.CartItemRequest;
import com.example.E_commerce_api.model.dto.response.CartItemResponse;
import com.example.E_commerce_api.model.dto.response.CartResponse;

public interface ICartService {

    String  addToCart(CartItemRequest request);
    CartResponse updateCart(CartItemRequest request);
    CartResponse removeFromCart(Long productId);
    CartResponse getCart();
}
