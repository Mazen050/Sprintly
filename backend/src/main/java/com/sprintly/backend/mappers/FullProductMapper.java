package com.sprintly.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.sprintly.backend.dtos.FullProductDto;
import com.sprintly.backend.entities.Products;
import com.sprintly.backend.entities.ProductImages;

import java.util.List;

@Mapper(
  componentModel = "spring",    
  uses = { ReviewMapper.class}
)
public interface FullProductMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "category")

    @Mapping(source = "name", target = "title")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "stockQuantity", target = "stockQuantity")

    @Mapping(source = "rating", target = "rating")
    @Mapping(source = "reviewCount", target = "reviews")

    @Mapping(source = "new", target = "isNew")
    @Mapping(source = "sale", target = "isSale")

@Mapping(
    target = "image",
    expression = "java(product.getProductProductImages() == null || product.getProductProductImages().isEmpty() "
      + "? null "
      + ": product.getProductProductImages().stream()"
      + ".filter(i -> Boolean.TRUE.equals(i.getIsPrimary()))"
      + ".findFirst()"
      + ".orElse(product.getProductProductImages().iterator().next())"
      + ".getImageUrl())"
)
@Mapping(
    source = "productReviews",
    target = "reviewList"
)


    FullProductDto toDto(Products product);



    @Named("primaryImage")
    default String mapPrimaryImage(List<ProductImages> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }

        // prefer primary image
        return images.stream()
                .filter(ProductImages::getIsPrimary)
                .findFirst()
                .orElse(images.get(0))
                .getImageUrl();
    }
}
