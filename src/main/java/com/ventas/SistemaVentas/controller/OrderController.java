package com.ventas.SistemaVentas.controller;

import com.ventas.SistemaVentas.dto.OrderRequest;
import com.ventas.SistemaVentas.model.Order;
import com.ventas.SistemaVentas.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.getAllOrders();
    }
}