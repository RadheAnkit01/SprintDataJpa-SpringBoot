package com.radheankit.SpringBootLearning.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String errorMessage){super(errorMessage);}
}
