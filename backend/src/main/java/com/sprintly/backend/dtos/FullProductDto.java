package com.sprintly.backend.dtos;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class FullProductDto {
    private Long id;
    private Long categoryId;
    private String image;
    private String title;
    private String category;
    private BigDecimal price;
    private Double rating;
    private Integer reviews;
    private Boolean isNew;
    private Boolean isSale;
    private Integer stockQuantity;
    private String description;
    private List<ReviewDto> reviewList;

    // private  reviews;
}
