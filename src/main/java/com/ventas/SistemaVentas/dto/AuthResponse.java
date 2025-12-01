package com.ventas.SistemaVentas.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private UserDto user;

    @Data
    public static class UserDto {
        private Long id;
        private String username;
        private String role;
        private String name;
    }
}