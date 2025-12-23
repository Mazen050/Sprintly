package com.sprintly.backend.dtos;

import lombok.Data;

@Data
public class ProductDto {
    
    private Long id;
    //private Long categoryId;
    private String image;
    private String title;
    private String category;
    private Double price;
    private Double originalPrice;
    private Double rating;
    private Integer reviews;
    private Boolean isNew;
    private Boolean isSale;
    // private Boolean isWishlisted;
}

    
