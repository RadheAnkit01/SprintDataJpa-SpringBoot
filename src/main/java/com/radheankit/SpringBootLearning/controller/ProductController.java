package com.radheankit.SpringBootLearning.controller;

import com.radheankit.SpringBootLearning.dto.CreateProductDto;
import com.radheankit.SpringBootLearning.dto.ProductDto;
import com.radheankit.SpringBootLearning.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    //User, Admin - Get all active products;
    @GetMapping
    ResponseEntity<List<ProductDto>> getAllActiveProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllActiveProducts());
    }

    @GetMapping("/search")
    ResponseEntity<List<ProductDto>> getSearchedProducts(@RequestParam String searchedItem){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getSearchedProducts(searchedItem));
    }

    //Admin - get all products
    @GetMapping("/admin")
    ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
    }
    @PostMapping
    ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductDto createProductDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(createProductDto));
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody CreateProductDto createProductDto){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id,createProductDto));
    }

    @DeleteMapping("/{id}") //Soft delete, inactive only
    ResponseEntity<Void> inactiveProduct(@PathVariable Long id){
        productService.inactiveProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/{id}/delete") //Permanent delete, inactive only
    ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
