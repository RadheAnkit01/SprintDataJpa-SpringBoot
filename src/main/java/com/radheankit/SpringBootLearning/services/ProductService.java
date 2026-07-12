package com.radheankit.SpringBootLearning.services;

import com.radheankit.SpringBootLearning.dto.CreateProductDto;
import com.radheankit.SpringBootLearning.dto.ProductDto;
import com.radheankit.SpringBootLearning.entities.Product;
import com.radheankit.SpringBootLearning.exception.ProductNotFoundException;
import com.radheankit.SpringBootLearning.repositories.ProductRepositiry;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepositiry productRepositiry;

    public List<ProductDto> getAllActiveProducts() {
        List<Product> products = productRepositiry.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products){
            if(product.getActive()){
                ProductDto productDto = new ProductDto(product.getId(), product.getName(), product.getPrice(), true);
                productDtos.add(productDto);
            }
        }
        return productDtos;
    }
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepositiry.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products){
            ProductDto productDto = new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getActive());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepositiry.findById(id).orElseThrow(()-> new ProductNotFoundException("Product Not Found!"));
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getActive());
    }

    public ProductDto createProduct( CreateProductDto createProductDto) {
        Product product = new Product();
        product.setActive(createProductDto.getActive());
        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());
        Product savedProduct = productRepositiry.save(product);
        return new ProductDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(),savedProduct.getActive());
    }
    @Transactional
    public ProductDto updateProduct(Long id, CreateProductDto createProductDto) {
        Product product = productRepositiry.findById(id).orElseThrow(()-> new ProductNotFoundException("Product Not Found!"));

        product.setName(createProductDto.getName());
        product.setPrice(createProductDto.getPrice());
        product.setActive(createProductDto.getActive());

        return new ProductDto(product.getId(), product.getName(), product.getPrice(),product.getActive());
    }

    public void deleteProduct(Long id) {
        productRepositiry.deleteById(id);
    }

    @Transactional
    public void inactiveProduct(Long id) {
        Product product = productRepositiry.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found"));
        product.setActive(false);
    }
}
