package com.example.E_commerce_api.service.impl;

import com.example.E_commerce_api.Exception.BadRequestException;
import com.example.E_commerce_api.Exception.ResourceNotFoundException;
import com.example.E_commerce_api.model.*;
import com.example.E_commerce_api.model.dto.request.OrderRequest;
import com.example.E_commerce_api.model.dto.request.updateOrderStatusRequest;
import com.example.E_commerce_api.model.dto.response.OrderItemResponse;
import com.example.E_commerce_api.model.dto.response.OrderResponse;
import com.example.E_commerce_api.model.enums.OrderStatus;
import com.example.E_commerce_api.repository.CartRepository;
import com.example.E_commerce_api.repository.OrderRepository;
import com.example.E_commerce_api.repository.ProductRepository;
import com.example.E_commerce_api.service.inf.IOrderService;
import com.example.E_commerce_api.service.inf.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor

public class OrderServiceImpl implements IOrderService {

    private final IUserService userService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User currentUser = userService.getCurrentUser();
        Cart cart = cartRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("cart not found"));
        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("cart is empty!");
        }

        Order order = Order.builder()
                .user(currentUser)
                .shippingAddress(request.getShippingAddress())
                .status(OrderStatus.PENDING)
                .totalAmount(0.0)
                .build();

        double total = 0;
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            if (product.getStockQuantity() < cartItem.getQuantity())
                throw new BadRequestException("There is no enough stock for product!:" + product.getTitle());

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .priceAtPurchase(product.getPrice())
                    .quantity(cartItem.getQuantity())
                    .product(product)
                    .build();

            total = 0.0;

            order.getItems().add(orderItem);

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
            total = total + (product.getPrice() * cartItem.getQuantity());
        }
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return mapToOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getMyOrders() {
        User currentUser = userService.getCurrentUser();
        return orderRepository.findByUserId(currentUser.getId()).stream()
                .map(this::mapToOrderResponse)
                .toList();

    }

    @Override
    public OrderResponse getOrderBYId(Long id) {
        return mapToOrderResponse(orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found with id: "+id)));
    }

    @Override
    public OrderResponse updateOrderStatus(updateOrderStatusRequest request) {
       Order order = orderRepository.findById(request.getId())
               .orElseThrow(()->new ResourceNotFoundException("Order not found with id: "+request.getId()));
        User currentUser = userService.getCurrentUser();

        if(order.getStatus().equals(OrderStatus.DELIVERED) || order.getStatus().equals(OrderStatus.CANCELLED))
                   throw new BadRequestException("cannot update status when it is delivered or cancelled!");

        order.setStatus(request.getStatus());

        if(request.getStatus().equals(OrderStatus.CANCELLED)){
            for(OrderItem orderItem: order.getItems()){
                Product product = orderItem.getProduct();
                product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
                productRepository.save(product);
            }
        }

        Order updateOrder = orderRepository.save(order);
        return mapToOrderResponse(updateOrder);
    }


    private OrderResponse mapToOrderResponse(Order order){
        List<OrderItemResponse> orderItems = order.getItems().stream()
                .map(this::mapToOrderItemResponse)
                .toList();
        return OrderResponse.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .shippingAddress(order.getShippingAddress())
                .totalAmount(order.getTotalAmount())
                .UserId(order.getUser().getId())
                .items(orderItems)
                .build();
    }
    private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem){
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .Quantity(orderItem.getQuantity())
                .priceAtPurchase(orderItem.getPriceAtPurchase())
                .productTitle(orderItem.getProduct().getTitle())
                .productBrand(orderItem.getProduct().getBrand())
                .productModel(orderItem.getProduct().getModel())
                .subTotal(orderItem.getQuantity() * orderItem.getPriceAtPurchase())
                .build();
    }
}
