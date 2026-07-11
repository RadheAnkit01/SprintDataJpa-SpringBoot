package com.radheankit.SpringBootLearning.controller;


import com.radheankit.SpringBootLearning.dto.CreateUserDto;
import com.radheankit.SpringBootLearning.dto.LoginUserDto;
import com.radheankit.SpringBootLearning.dto.LoginUserResponseDto;
import com.radheankit.SpringBootLearning.dto.RegisterUserResponseDto;
import com.radheankit.SpringBootLearning.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody CreateUserDto createUserDto){

    return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(createUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> loginUser(@RequestBody LoginUserDto loginUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(authService.loginUser(loginUserDto));
    }
}
