package com.ventas.SistemaVentas.controller;

import com.ventas.SistemaVentas.dto.OrderRequest;
import com.ventas.SistemaVentas.model.Order;
import com.ventas.SistemaVentas.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders") // Ruta: http://localhost:8080/api/v1/orders
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST: Crear una nueva venta (Desde el Checkout del Frontend)
    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // GET: Listar todas las ventas (Para el Reporte del Admin)
    @GetMapping
    public List<Order> getAll() {
        return orderService.getAllOrders();
    }
}