package com.radheankit.SpringBootLearning.services;

import com.radheankit.SpringBootLearning.dto.CreateOrderDto;
import com.radheankit.SpringBootLearning.dto.OrderDto;
import com.radheankit.SpringBootLearning.dto.ProductDto;
import com.radheankit.SpringBootLearning.dto.UserDto;
import com.radheankit.SpringBootLearning.entities.Order;
import com.radheankit.SpringBootLearning.entities.Product;
import com.radheankit.SpringBootLearning.entities.User;
import com.radheankit.SpringBootLearning.exception.OrderNotFoundException;
import com.radheankit.SpringBootLearning.exception.ProductNotFoundException;
import com.radheankit.SpringBootLearning.repositories.OrderRepository;
import com.radheankit.SpringBootLearning.repositories.ProductRepositiry;
import com.radheankit.SpringBootLearning.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductRepositiry productRepositiry;

    //Helping method
    private OrderDto mapOrderDto(Order order){
        return new OrderDto(
                order.getId(),
                new ProductDto(
                        order.getProduct().getId(),
                        order.getProduct().getName(),
                        order.getProduct().getPrice(),
                        order.getProduct().getActive()
                ),
                new UserDto(
                        order.getUser().getId(),
                        order.getUser().getName(),
                        order.getUser().getEmail()
                ),
                order.getPriceAtPurchase()
        );

    }

    @Transactional
    public OrderDto creatMyOrder( CreateOrderDto createOrderDto) {
        User user = userService.getLoggedInUser();
        Product product = productRepositiry.findById(createOrderDto.getProductId()).orElseThrow(()-> new ProductNotFoundException("Product not found"));
        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setPriceAtPurchase(product.getPrice());
        Order savedOrder = orderRepository.save(order);
        return mapOrderDto(savedOrder);
    }

    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order : orders){
            OrderDto orderDto = mapOrderDto(order);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public List<OrderDto> getMyOrders() {
        User user = userService.getLoggedInUser();
        List<Order> orders = orderRepository.findByUserId(user.getId());
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order : orders){
            orderDtos.add(mapOrderDto(order));
        }
        return orderDtos;
    }

    public OrderDto getMyOrderById(Long id) {
        User user = userService.getLoggedInUser();
        Order order = orderRepository.findById(id).orElseThrow(()->new OrderNotFoundException("Order Not Found"));
        if(!order.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Access Denied");
        }
        return mapOrderDto(order);
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order: orders){
            OrderDto orderDto = mapOrderDto(order);
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
