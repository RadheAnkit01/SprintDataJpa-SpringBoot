package com.radheankit.SpringDataJpa.dto;

import com.radheankit.SpringDataJpa.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
public class CreateOrderDto {
    private String productName;
}
