package com.sprintly.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class UserDto {
    
    private Long id;
    private String name;
    private String email;
}
