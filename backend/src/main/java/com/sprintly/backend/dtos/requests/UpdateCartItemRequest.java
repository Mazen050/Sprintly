package com.sprintly.backend.dtos.requests;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
    private Long productId;
    private Integer quantity;
}
