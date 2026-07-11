package com.radheankit.SpringBootLearning.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;
    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;
}
