package com.radheankit.SpringDataJpa.services;

import com.radheankit.SpringDataJpa.dto.CreateOrderDto;
import com.radheankit.SpringDataJpa.dto.OrderDto;
import com.radheankit.SpringDataJpa.dto.UserDto;
import com.radheankit.SpringDataJpa.entities.Order;
import com.radheankit.SpringDataJpa.entities.User;
import com.radheankit.SpringDataJpa.repositories.OrderRepository;
import com.radheankit.SpringDataJpa.repositories.UserRepository;
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

    @Transactional
    public OrderDto creatOrder(Long userId, CreateOrderDto createOrderDto) {
        User user = userRepository.findById(userId).orElseThrow();
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
}
