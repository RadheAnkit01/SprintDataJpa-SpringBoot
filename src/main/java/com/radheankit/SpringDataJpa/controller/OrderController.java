package com.radheankit.SpringDataJpa.controller;


import com.radheankit.SpringDataJpa.dto.CreateOrderDto;
import com.radheankit.SpringDataJpa.dto.OrderDto;
import com.radheankit.SpringDataJpa.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
//    @GetMapping
//    public ResponseEntity<OrderDto> getOrders(@PathVariable Long id, @RequestBody CreateOrderDto createOrderDto){
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders(id));
//    }

    @PostMapping
    public ResponseEntity<OrderDto> createOder(@PathVariable Long userId, @RequestBody CreateOrderDto createOrderDto){
        System.out.println("createOrder from orderController");
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.creatOrder(userId,createOrderDto));
    }

    //get all orders of user

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUserId(userId));
    }
}
