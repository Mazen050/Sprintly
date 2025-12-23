package com.sprintly.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sprintly.backend.dtos.ProductDto;
import com.sprintly.backend.entities.Products;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "name", target = "title")
    @Mapping(source = "category.name", target = "category")
    ProductDto toDto(Products product);
    
    @Mapping(source = "title", target = "name")
    @Mapping(target = "category", ignore = true) 
    Products toEntity(ProductDto productDto);
}

