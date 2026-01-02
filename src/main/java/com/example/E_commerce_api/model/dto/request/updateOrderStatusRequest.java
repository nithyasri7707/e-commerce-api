package com.example.E_commerce_api.model.dto.request;

import com.example.E_commerce_api.model.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class updateOrderStatusRequest {

    @NotNull(message = "Id is required!")
    private Long id;

    @NotNull(message = "Status is required!")
    private OrderStatus status;

}
