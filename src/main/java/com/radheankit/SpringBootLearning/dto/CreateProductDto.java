package com.radheankit.SpringBootLearning.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductDto {
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;
    @NotNull
    @NotBlank
    @NotEmpty
    private BigDecimal price;
    @NotNull
    @NotBlank
    @NotEmpty
    private Boolean active;

}
