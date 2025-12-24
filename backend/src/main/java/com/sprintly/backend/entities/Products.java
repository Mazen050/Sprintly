package com.sprintly.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Products {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column
    private Integer stockQuantity;

    @Column
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Categories category;

    @OneToMany(mappedBy = "product")
    private Set<ProductImages> productProductImages = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ProductVariants> productProductVariants = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderItems> productOrderItems = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Reviews> productReviews = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<CartItems> productCartItems = new HashSet<>();

        @Column(nullable = false)
        private boolean isNew;

        @Column(nullable = false)
        private boolean isSale;

        @Column
        @Min(1)
        @Max(5)
        private Integer rating;

        @Column(nullable = false)
        private int reviewCount;

    
}
