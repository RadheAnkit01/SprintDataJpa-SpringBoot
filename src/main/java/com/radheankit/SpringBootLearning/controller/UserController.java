package com.radheankit.SpringBootLearning.controller;


import com.radheankit.SpringBootLearning.dto.CreateUserDto;
import com.radheankit.SpringBootLearning.dto.UserDto;
import com.radheankit.SpringBootLearning.services.UserService;
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

    //User APIs logged in user only.  - identify id by jwt token;
    //User - get my details or profile
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(){
        System.out.println("getMe called");
        return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentUser());
    }
    //User
    @PutMapping("/me")
    public ResponseEntity<UserDto> updateMe(@RequestBody CreateUserDto createUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateCurrentUser(createUserDto));
    }

    //Admin
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    //Admin
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.gerUserById(id));
    }
    //Admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //Admin
    @PatchMapping("/{id}")
    public  ResponseEntity<UserDto> patchUser(@PathVariable Long id, @RequestBody CreateUserDto patchUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.patchUser(id, patchUserDto));
    }
    //Admin
    @PutMapping("/{id}")
    public  ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody CreateUserDto updateUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, updateUserDto));
    }
    //Admin
    @GetMapping("/paginated")
    public ResponseEntity<List<UserDto>> getUsersPaginated(@RequestParam int page, @RequestParam int pageSize,
                                                           @RequestParam(defaultValue = "asc") String direction,
                                                           @RequestParam(defaultValue = "name") String sortBy){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersPaginated(page,pageSize,direction,sortBy));
    }

}
