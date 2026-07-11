package com.radheankit.SpringBootLearning.services;


import com.radheankit.SpringBootLearning.dto.CreateUserDto;
import com.radheankit.SpringBootLearning.dto.UserDto;
import com.radheankit.SpringBootLearning.entities.User;
import com.radheankit.SpringBootLearning.exception.UserNotFoundException;
import com.radheankit.SpringBootLearning.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
        User user =  userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found "));
        return map(user);
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


    public List<UserDto> getUsersPaginated(int page, int pageSize, String direction, String sortBy) {
        Sort sort;
        sort = direction.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable =  PageRequest.of(page,pageSize,sort);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserDto> userDtoList = new ArrayList<>();
        userPage.forEach(user -> userDtoList.add(new UserDto(user.getId(), user.getName(), user.getEmail())));

        return userDtoList;
    }

    protected User getLoggedInUser(){
        String email = Objects.requireNonNull(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName();
        return userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));
    }
    private UserDto map(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
    public UserDto getCurrentUser() {
        System.out.println("GetCurrentUser Called");
        User user = getLoggedInUser();
        System.out.println(user);
        return map(user);
    }
    @Transactional
    public UserDto updateCurrentUser(CreateUserDto createUserDto) {
        User user = getLoggedInUser();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        return map(user);
    }
}
