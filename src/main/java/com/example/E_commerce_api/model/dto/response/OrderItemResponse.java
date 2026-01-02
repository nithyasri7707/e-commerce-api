package com.example.E_commerce_api.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {

    private Long id;
    private Integer Quantity;
    private Double priceAtPurchase;
    private String productTitle;
    private String productModel;
    private String productBrand;
    private double subTotal;

}
