package com.sprintly.backend.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckoutRequest {
    @NotNull
    private Long CartId;
}
