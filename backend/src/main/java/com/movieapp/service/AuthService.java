package com.movieapp.service;

import com.movieapp.dto.*;
import com.movieapp.model.Role;
import com.movieapp.model.User;
import com.movieapp.repository.UserRepository;
import com.movieapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user= User
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        userRepository.save(user);

        String token=jwtService.generateToken(
                user.getUsername(),user.getRole().name());
        return new AuthResponse(token);
    }
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        User user=userRepository.findByUsername(request.getUsername()).orElseThrow();

        String token=jwtService.generateToken(user.getUsername(),user.getRole().name());

        return new AuthResponse(token);
    }
}
