package com.radheankit.SpringDataJpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginUserResponseDto {
    private String jwt;
}
