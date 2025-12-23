package com.sprintly.backend.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sprintly.backend.dtos.CartDto;
import com.sprintly.backend.dtos.CartItemDto;
import com.sprintly.backend.entities.CartItems;
import com.sprintly.backend.entities.Carts;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Carts cart);


    @Mapping(target = "totalPrice", expression = "java(item.getTotalPrice())")
    CartItemDto toDto(CartItems item);
}