package com.radheankit.SpringDataJpa.controller;


import com.radheankit.SpringDataJpa.dto.CreateUserDto;
import com.radheankit.SpringDataJpa.dto.OrderDto;
import com.radheankit.SpringDataJpa.dto.UserDto;
import com.radheankit.SpringDataJpa.services.OrderService;
import com.radheankit.SpringDataJpa.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto createUserDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(createUserDto));
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @GetMapping("/paginated")
    public ResponseEntity<List<UserDto>> getUsersPaginated(@RequestParam int page, @RequestParam int pageSize,
                                                           @RequestParam(defaultValue = "asc") String direction,
                                                           @RequestParam(defaultValue = "name") String sortBy){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersPaginated(page,pageSize,direction,sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.gerUserById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping("/{id}")
    public  ResponseEntity<UserDto> patchUser(@PathVariable Long id, @RequestBody CreateUserDto patchUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchUser(id, patchUserDto));
    }

    @PutMapping("/{id}")
    public  ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody CreateUserDto updateUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, updateUserDto));
    }




}
