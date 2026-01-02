package com.example.E_commerce_api.repository;

import com.example.E_commerce_api.model.Order;
import com.example.E_commerce_api.model.dto.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long id);
}
