package com.example.E_commerce_api.controller;


import com.example.E_commerce_api.model.dto.request.OrderRequest;
import com.example.E_commerce_api.model.dto.request.updateOrderStatusRequest;
import com.example.E_commerce_api.model.dto.response.OrderResponse;
import com.example.E_commerce_api.service.inf.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping()
    public ResponseEntity<OrderResponse>createOrder(@Valid @RequestBody OrderRequest orderRequest){
        OrderResponse response = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>>getMyOrders(){
        List<OrderResponse> orders = orderService.getMyOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse>getOrderBYId(@PathVariable Long id){
        OrderResponse order = orderService.getOrderBYId(id);
        return ResponseEntity.ok(order);
    }
    @PutMapping()
    public ResponseEntity<OrderResponse> updateOrderStatus(@Valid @RequestBody updateOrderStatusRequest request){
        OrderResponse order = orderService.updateOrderStatus(request);
        return ResponseEntity.ok(order);
    }
}
