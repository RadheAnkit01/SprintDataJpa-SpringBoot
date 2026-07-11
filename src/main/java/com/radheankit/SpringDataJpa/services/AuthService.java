package com.radheankit.SpringDataJpa.services;

import com.radheankit.SpringDataJpa.dto.CreateUserDto;
import com.radheankit.SpringDataJpa.dto.LoginUserDto;
import com.radheankit.SpringDataJpa.dto.LoginUserResponseDto;
import com.radheankit.SpringDataJpa.dto.RegisterUserResponseDto;
import com.radheankit.SpringDataJpa.entities.Role;
import com.radheankit.SpringDataJpa.entities.User;
import com.radheankit.SpringDataJpa.repositories.UserRepository;
import com.radheankit.SpringDataJpa.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public RegisterUserResponseDto registerUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        return new RegisterUserResponseDto(savedUser.getName(),savedUser.getId());
    }

    public LoginUserResponseDto loginUser(LoginUserDto loginUserDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(),loginUserDto.getPassword())
        );

        String jwtToken = jwtService.generateJwtToken((UserDetails) Objects.requireNonNull(authentication.getPrincipal()));

        return  new LoginUserResponseDto(jwtToken);
    }
}
