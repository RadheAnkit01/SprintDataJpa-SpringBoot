package com.radheankit.SpringBootLearning.repositories;

import com.radheankit.SpringBootLearning.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepositiry extends JpaRepository<Product,Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
}
