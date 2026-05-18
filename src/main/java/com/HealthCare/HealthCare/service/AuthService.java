package com.HealthCare.HealthCare.service;

import com.HealthCare.HealthCare.Security.JwtService;
import com.HealthCare.HealthCare.dto.UserDto;
import com.HealthCare.HealthCare.model.User;
import com.HealthCare.HealthCare.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String register(UserDto req){
        if(userRepository.existsByUsername(req.getUsername())){
            throw new IllegalArgumentException("Username déja utilisé");
        }
        if (userRepository.existsByEmail(req.getEmail())){
            throw new  IllegalArgumentException("Email deja utilisé");
        }
        User user= new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);

        return jwtService.generateToken(user.getUsername());
    }

    public String login(UserDto req){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(),req.getPassword())
        );
        User user = userRepository.findByUsername(req.getUsername()).orElseThrow(()-> new UsernameNotFoundException("utilisateur introuvable"));
        return jwtService.generateToken(user.getUsername());
    }

}