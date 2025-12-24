package com.sprintly.backend.dtos;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class ReviewDto {
    private Integer rating;
    private String comment;
    private OffsetDateTime createdAt;
}
