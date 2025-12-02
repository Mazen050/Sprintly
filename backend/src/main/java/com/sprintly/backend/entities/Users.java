package com.sprintly.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "\"Users\"")
@Getter
@Setter
public class Users {

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

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, columnDefinition = "text")
    private String passwordHash;

    @Column(length = 20)
    private String phone;

    @Column
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private Set<Addresses> userAddresses = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Orders> userOrders = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Reviews> userReviews = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Carts> userCarts = new HashSet<>();

}
