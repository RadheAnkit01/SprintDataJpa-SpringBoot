package com.radheankit.SpringDataJpa.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(max = 8)
    private String name;
    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    private String email;
}
