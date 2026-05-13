package com.HealthCare.HealthCare.controller;

import com.HealthCare.HealthCare.Security.JwtService;
import com.HealthCare.HealthCare.dto.UserDto;
import com.HealthCare.HealthCare.exception.ResourceNotFoundException;
import com.HealthCare.HealthCare.mapper.UserMapper;
import com.HealthCare.HealthCare.model.User;
import com.HealthCare.HealthCare.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto){
        log.info("Registering user with email: {}", userDto.getEmail());
        if (userRepository.existsByEmail(userDto.getEmail())){
            return ResponseEntity.badRequest().body("Email already used");
        }
        if (userDto.getRole() == null){
            userDto.setRole("ROLE_PATIENT");
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto userDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername() , userDto.getPassword())
        );

        var user = userRepository.findByUsername(userDto.getUsername()).orElseThrow(()-> new ResourceNotFoundException("Credentials incorrect"));

        String token = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRole()))
                )
        );

        return ResponseEntity.ok(token);
    }

}
