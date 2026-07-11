package com.radheankit.SpringBootLearning.exception;

import com.radheankit.SpringBootLearning.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto( "USER_NOT_FOUND",userNotFoundException.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        HashMap<String,String> fieldError = new HashMap<>();
//        String error = "getMessage : " + ex.getMessage() + " getLocalizedMessage : " + ex.getLocalizedMessage()
//                + " getBody : " + ex.getBody() + " getDetailedMessageCode : " + ex.getDetailMessageCode()
//                + " getTitleMessageCode : " + ex.getTitleMessageCode() + " getTypeMessageCode : " + ex.getTypeMessageCode()
//                + " getBindingResult : " + ex.getBindingResult() + " getBody : " + ex.getFieldError()
//                + " getBody : " + ex.getFieldErrors();

        ex.getFieldErrors().forEach(err -> {
            fieldError.put(err.getField(),err.getDefaultMessage());
        });

        StringBuilder errorMessage = new StringBuilder();
        for(String field : fieldError.keySet()){
            errorMessage.append(field).append(" : ").append(fieldError.get(field)).append("\n");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto("INVALID_INPUT", errorMessage.toString()));
    }
}
