package com.sprintly.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sprintly.backend.dtos.ProductCreateDto;
import com.sprintly.backend.dtos.ProductDto;
import com.sprintly.backend.entities.Products;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "name", target = "title")
    @Mapping(source = "category.id", target = "categoryId") // safe
    @Mapping(source = "category.name", target = "category") // safe
    // @Mapping(target = "category", ignore = true)            // ✅ IMPORTANT
    // @Mapping(target = "isSale", ignore = true)              // set manually
    ProductDto toDto(Products product);
    
    // DTO -> Entity
    @Mapping(source = "title", target = "name")
    @Mapping(target = "category", ignore = true) 
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productCartItems", ignore = true)
    @Mapping(target = "productOrderItems", ignore = true)
    @Mapping(target = "productProductImages", ignore = true)
    @Mapping(target = "productProductVariants", ignore = true)
    @Mapping(target = "productReviews", ignore = true)
    Products toEntity(ProductCreateDto dto);
}


