package com.ventas.SistemaVentas.controller;

import com.ventas.SistemaVentas.dto.AuthResponse;
import com.ventas.SistemaVentas.dto.LoginRequest;
import com.ventas.SistemaVentas.dto.RegisterRequest;
import com.ventas.SistemaVentas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth") // Esto se suma al /api/v1 del properties -> /api/v1/auth
public class AuthController {

    private final AuthService authService;

    // Inyección manual segura
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}