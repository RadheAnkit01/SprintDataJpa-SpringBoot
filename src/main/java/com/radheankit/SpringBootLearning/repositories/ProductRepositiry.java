package com.radheankit.SpringBootLearning.repositories;

import com.radheankit.SpringBootLearning.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositiry extends JpaRepository<Product,Long> {
}
