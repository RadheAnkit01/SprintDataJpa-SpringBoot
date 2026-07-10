package com.radheankit.SpringDataJpa.exception;

import com.radheankit.SpringDataJpa.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto( "USER_NOT_FOUND",userNotFoundException.getMessage()));
    }
}
