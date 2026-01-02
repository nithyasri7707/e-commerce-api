package com.example.E_commerce_api.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class WishListItemRequest {

    @NotNull(message = "product ID is required!")
    private Long productId;

}
