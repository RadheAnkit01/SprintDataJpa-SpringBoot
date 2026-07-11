package com.radheankit.SpringBootLearning.services;

import com.radheankit.SpringBootLearning.dto.CreateOrderDto;
import com.radheankit.SpringBootLearning.dto.OrderDto;
import com.radheankit.SpringBootLearning.dto.UserDto;
import com.radheankit.SpringBootLearning.entities.Order;
import com.radheankit.SpringBootLearning.entities.User;
import com.radheankit.SpringBootLearning.exception.OrderNotFoundException;
import com.radheankit.SpringBootLearning.repositories.OrderRepository;
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
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public OrderDto creatMyOrder( CreateOrderDto createOrderDto) {
        User user = userService.getLoggedInUser();
        Order order = new Order();
        order.setUser(user);
        order.setProductName(createOrderDto.getProductName());
        Order savedOrder = orderRepository.save(order);
        return new OrderDto(savedOrder.getId(), savedOrder.getProductName(),new UserDto(savedOrder.getUser().getId(), savedOrder.getUser().getName(), savedOrder.getUser().getEmail()));
    }

    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order : orders){
            OrderDto orderDto = new OrderDto(order.getId(),order.getProductName(),new UserDto(order.getUser().getId(), order.getUser().getName(), order.getUser().getEmail()) );
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public List<OrderDto> getMyOrders() {
        User user = userService.getLoggedInUser();
        List<Order> orders = orderRepository.findByUserId(user.getId());
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order : orders){
            orderDtos.add(new OrderDto(order.getId(),order.getProductName(),new UserDto(user.getId(), user.getName(), user.getEmail())));
        }
        return orderDtos;
    }

    public OrderDto getMyOrderById(Long id) {
        User user = userService.getLoggedInUser();
        Order order = orderRepository.findById(id).orElseThrow(()->new OrderNotFoundException("Order Not Found"));
        if(!order.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Access Denied");
        }
        return new OrderDto(order.getId(), order.getProductName(), new UserDto(user.getId(), user.getName(), user.getEmail()));
    }

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order: orders){
            OrderDto orderDto = new OrderDto(order.getId(), order.getProductName(),
                    new UserDto(order.getUser().getId(),order.getUser().getName(),order.getUser().getEmail()));
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
