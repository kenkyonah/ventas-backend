package com.ventas.SistemaVentas.repository;

import com.ventas.SistemaVentas.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Aquí podríamos agregar métodos para reportes después
    // Ejemplo: List<Order> findByPaymentMethod(String method);
}