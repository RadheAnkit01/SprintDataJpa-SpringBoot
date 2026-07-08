package com.radheankit.SpringDataJpa.services;


import com.radheankit.SpringDataJpa.dto.CreateUserDto;
import com.radheankit.SpringDataJpa.dto.UserDto;
import com.radheankit.SpringDataJpa.entities.User;
import com.radheankit.SpringDataJpa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public UserDto saveUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    public List<UserDto> getUsers() {
        List<User> users= userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : users){
            UserDto userDto = new UserDto(user.getId(),user.getName(), user.getEmail());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }


    public UserDto gerUserById(Long id) {
        User user =  userRepository.findById(id).orElseThrow();
        return new UserDto(user.getId(),user.getName(), user.getEmail());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDto updateUser(Long id, CreateUserDto updateUserDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setEmail(updateUserDto.getEmail());
        user.setName(updateUserDto.getName());

        //here no save() called still works because of @Transactional
        //Hybernate will automatic saved it.

        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
    @Transactional
    public UserDto patchUser(Long id, CreateUserDto patchUserDto) {
        User user = userRepository.findById(id).orElseThrow();

        if(patchUserDto.getName()!= null){
            user.setName(patchUserDto.getName());
        }
        if(patchUserDto.getEmail()!= null){
            user.setEmail(patchUserDto.getEmail());
        }
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
