package com.radheankit.SpringBootLearning.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
