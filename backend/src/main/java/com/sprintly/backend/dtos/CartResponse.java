package com.sprintly.backend.dtos;

import lombok.Data;
import java.util.List;

@Data
public class CartResponse {

    private Long cartId;
    private List<CartItemResponse> items;
}