package com.example.E_commerce_api.service.inf;

import com.example.E_commerce_api.model.dto.request.WishListItemRequest;
import com.example.E_commerce_api.model.dto.response.WishlistResponse;

public interface IWishlistService {

    String addToWishlist(WishListItemRequest request);
    WishlistResponse removeFromWishlist(Long id);
    WishlistResponse getWishlist();
}
