package com.radheankit.SpringDataJpa.repositories;

import com.radheankit.SpringDataJpa.dto.OrderDto;
import com.radheankit.SpringDataJpa.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
}
