package com.radheankit.SpringBootLearning.controller;


import com.radheankit.SpringBootLearning.dto.CreateOrderDto;
import com.radheankit.SpringBootLearning.dto.OrderDto;
import com.radheankit.SpringBootLearning.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;





@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    //User - create order
    @PostMapping("/my")
    public ResponseEntity<OrderDto> createMyOder(@RequestBody CreateOrderDto createOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.creatMyOrder(createOrderDto));
    }

    //User - get all my orders
    @GetMapping("/my")
    public ResponseEntity<List<OrderDto>> getMyOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getMyOrders());
    }
    //User - get my order by order id, I want to search it;
    @GetMapping("/my/{id}")
    public ResponseEntity<OrderDto> getMyOrderById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getMyOrderById(id));
    }

    //Admin - get all orders
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
    }

    //Admin - get all orders of user
    @GetMapping("/users/{id}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUserId(userId));
    }

    //Admin - Delete order by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}





//Previously

//
//@RestController
//@RequestMapping("/api/v1/users/{userId}/orders")
//@AllArgsConstructor
//public class OrderController {
//    private final OrderService orderService;
////    @GetMapping
////    public ResponseEntity<OrderDto> getOrders(@PathVariable Long id, @RequestBody CreateOrderDto createOrderDto){
////        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrders(id));
////    }
//
//    @PostMapping
//    public ResponseEntity<OrderDto> createOder( @PathVariable Long userId, @RequestBody CreateOrderDto createOrderDto){
//        System.out.println("createOrder from orderController");
//        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.creatOrder(userId,createOrderDto));
//    }
//
//    //get all orders of user
//
//    @GetMapping
//    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable Long userId){
//        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrdersByUserId(userId));
//    }
//}
