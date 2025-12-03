package com.ventas.SistemaVentas.repository;

import com.ventas.SistemaVentas.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz para interactuar con la Base de Datos.
 * Extiende de JpaRepository para tener métodos gratis como:
 * save(), findAll(), findById(), deleteById()...
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas en el futuro
    // Ejemplo: List<Product> findByNameContaining(String name);
}