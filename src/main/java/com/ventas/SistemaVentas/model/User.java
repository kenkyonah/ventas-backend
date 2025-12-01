package com.ventas.SistemaVentas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // Usamos 'users' porque 'user' es palabra reservada en SQL
@Data // Lombok genera todos los getters y setters automáticamente
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    // Rol: 'ADMIN', 'VENDEDOR', 'CLIENTE'
    @Column(nullable = false)
    private String role;
}