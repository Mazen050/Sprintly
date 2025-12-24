package com.sprintly.backend.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;

import com.sprintly.backend.dtos.ReviewDto;
import com.sprintly.backend.entities.Reviews;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewDto toDto(Reviews review);

    List<ReviewDto> toDtoList(Set<Reviews> reviews);
}

