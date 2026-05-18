package com.HealthCare.HealthCare.controller;

import com.HealthCare.HealthCare.Security.JwtService;
import com.HealthCare.HealthCare.dto.UserDto;
import com.HealthCare.HealthCare.exception.ResourceNotFoundException;
import com.HealthCare.HealthCare.mapper.UserMapper;
import com.HealthCare.HealthCare.model.User;
import com.HealthCare.HealthCare.repository.UserRepository;
import com.HealthCare.HealthCare.service.AuthService;
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
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(authService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(authService.login(userDto));
    }
}
