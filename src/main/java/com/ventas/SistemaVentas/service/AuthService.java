package com.ventas.SistemaVentas.service;

import com.ventas.SistemaVentas.dto.AuthResponse;
import com.ventas.SistemaVentas.dto.LoginRequest;
import com.ventas.SistemaVentas.dto.RegisterRequest;
import com.ventas.SistemaVentas.model.User;
import com.ventas.SistemaVentas.repository.UserRepository;
import com.ventas.SistemaVentas.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    // INYECCIÓN POR CONSTRUCTOR (Manual, sin Lombok)
    @Autowired
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtUtils.generateToken(user.getUsername(), user.getRole());

        return createAuthResponse(token, user);
    }

    // REGISTRO
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El usuario ya existe");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("CLIENTE");

        userRepository.save(user);
        String token = jwtUtils.generateToken(user.getUsername(), user.getRole());

        return createAuthResponse(token, user);
    }

    // Método auxiliar para no repetir código
    private AuthResponse createAuthResponse(String token, User user) {
        AuthResponse response = new AuthResponse();
        response.setToken(token);

        AuthResponse.UserDto userDto = new AuthResponse.UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setName(user.getName());

        response.setUser(userDto);
        return response;
    }
}