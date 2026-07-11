package com.radheankit.SpringBootLearning.repositories;

import com.radheankit.SpringBootLearning.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
}
