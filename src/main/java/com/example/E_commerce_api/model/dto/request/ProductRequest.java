package com.example.E_commerce_api.model.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class ProductRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title should be between 2 to 100 character")
    private String title;

    @NotBlank(message = "Brand is required")
    @Size(min = 2, max = 100, message = "brand should be between 4 to 100 character")
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(min = 2, max = 100, message = "model should be between 4 to 100 character")
    public String model;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 1000, message = "Description should be between 2 to 100 character")
    private String description;

    @NotNull(message = "price is required")
    @Min(value = 10, message = "Price should be between 2 to 100 character")
    private Double price;

    @NotNull(message = "StockQuantity is required")
    @Min(value= 2,message = "StockQuantity is required")
    private Integer stockQuantity;


    @NotBlank(message = "Category is required")
    @Size(min = 2, max = 100, message = "Category should be between 4 to 100 character")
    private String category;

    @NotBlank(message = "ImageUrl is required")
    private String imageUrl;


}
