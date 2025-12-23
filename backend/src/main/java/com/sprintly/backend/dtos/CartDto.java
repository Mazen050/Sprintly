package com.sprintly.backend.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CartDto {
 private Long id;
 private List<CartItemDto> items = new ArrayList<>();
 private BigDecimal totalPrice=BigDecimal.ZERO;
}
