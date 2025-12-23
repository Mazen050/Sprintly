package com.sprintly.backend.dtos;


import lombok.Data;

@Data
public class CartItemResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
}