package com.ventas.SistemaVentas.repository;

import com.ventas.SistemaVentas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Método mágico para buscar por nombre de usuario
    Optional<User> findByUsername(String username);

    // Métodos para verificar si ya existen (útil para el registro)
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}