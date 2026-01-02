package com.example.E_commerce_api.service.impl;

import com.example.E_commerce_api.Exception.BadRequestException;
import com.example.E_commerce_api.Exception.ResourceNotFoundException;
import com.example.E_commerce_api.model.Cart;
import com.example.E_commerce_api.model.CartItem;
import com.example.E_commerce_api.model.Product;
import com.example.E_commerce_api.model.User;
import com.example.E_commerce_api.model.dto.request.CartItemRequest;
import com.example.E_commerce_api.model.dto.response.CartItemResponse;
import com.example.E_commerce_api.model.dto.response.CartResponse;
import com.example.E_commerce_api.repository.CartItemRepository;
import com.example.E_commerce_api.repository.CartRepository;
import com.example.E_commerce_api.repository.ProductRepository;
import com.example.E_commerce_api.service.inf.ICartService;
import com.example.E_commerce_api.service.inf.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final IUserService userService;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;


    @Override
    @Transactional
    public String addToCart(CartItemRequest request) {
        User currentUser = userService.getCurrentUser();

        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseGet(()->createCartForUser(currentUser));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with Id: "+request.getProductId()));

        if(product.getStockQuantity() < request.getQuantity())
            throw new BadRequestException("no enough stock available");

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),product.getId()).orElse(null);

        if(cartItem!=null){
            cartItem.setQuantity(cartItem.getQuantity()+ request.getQuantity());
        }
        else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
            cart.addItem(cartItem);
        }
        cartRepository.save(cart);
        return "Product Added to cart Successfully!";
    }

    @Override
    @Transactional
    public CartResponse updateCart(CartItemRequest request) {
        User currentUser = userService.getCurrentUser();

        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseGet(()->createCartForUser(currentUser));

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), request.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: "));



        if(request.getQuantity() == 0){
            cart.removeItem(cartItem);
            cartItemRepository.delete(cartItem);
        }
        else {
            if(cartItem.getProduct().getStockQuantity() < request.getQuantity())
                throw new BadRequestException("no enough stock available");
            cartItem.setQuantity(request.getQuantity());
        }
        cartRepository.save(cart);
        return mapToCartResponse(cart);

    }

    @Override
    @Transactional
    public CartResponse removeFromCart(Long id) {
        User currentUser = userService.getCurrentUser();

        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: "));

        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    public CartResponse getCart() {
        User currentUser = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseGet(()->createCartForUser(currentUser));
        return mapToCartResponse(cart);
    }

    private Cart createCartForUser(User currentUser) {
        Cart cart = Cart.builder()
                .user(currentUser)
                .build();
        cartRepository.save(cart);
        return cart;
    }

    private CartResponse mapToCartResponse(Cart cart){
        List<CartItem> items = cart.getItems();
        List<CartItemResponse> responsesItems = cart.getItems().stream()
                .map(this::mapToCartItemResponse)
                .toList();
        double total = 0.0;
        for(CartItem item: items){
            total = total + (item.getProduct().getPrice() * item.getQuantity());
        }
        return CartResponse.builder()
                .id(cart.getId())
                .item(responsesItems)
                .totalAmount(total)
                .build();
    }

    private CartItemResponse mapToCartItemResponse(CartItem cartItem){
        double subTotal = 0.0;
        subTotal=cartItem.getQuantity() * cartItem.getProduct().getPrice();

        return CartItemResponse.builder()
                .id(cartItem.getId())
                .Quantity(cartItem.getQuantity())
                .productTitle(cartItem.getProduct().getTitle())
                .productBrand(cartItem.getProduct().getBrand())
                .productModel(cartItem.getProduct().getModel())
                .productPrice(cartItem.getProduct().getPrice())
                .Quantity(cartItem.getQuantity())
                .subTotal(subTotal)
                .build();

    }
}
