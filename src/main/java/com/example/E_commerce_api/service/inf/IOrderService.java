package com.example.E_commerce_api.service.inf;

import com.example.E_commerce_api.model.dto.request.OrderRequest;
import com.example.E_commerce_api.model.dto.request.updateOrderStatusRequest;
import com.example.E_commerce_api.model.dto.response.OrderResponse;

import java.util.List;

public interface IOrderService {

    OrderResponse createOrder(OrderRequest request);
    List<OrderResponse>  getMyOrders();
    OrderResponse getOrderBYId(Long id);
    OrderResponse updateOrderStatus(updateOrderStatusRequest request);

}
