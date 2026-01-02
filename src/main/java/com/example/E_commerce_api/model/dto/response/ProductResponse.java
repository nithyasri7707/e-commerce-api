package com.example.E_commerce_api.model.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private String title;
    private String brand;
    private String model;
    private String description;
    private Double price;
    private String category;
    private Integer stockQuantity;
    private String imageUrl;
    private Long sellerId;
    private String sellerName;

}
