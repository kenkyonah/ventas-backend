package com.ventas.SistemaVentas.service;

import com.ventas.SistemaVentas.dto.OrderRequest;
import com.ventas.SistemaVentas.model.Order;
import com.ventas.SistemaVentas.model.OrderItem;
import com.ventas.SistemaVentas.model.Product;
import com.ventas.SistemaVentas.model.User;
import com.ventas.SistemaVentas.repository.OrderRepository;
import com.ventas.SistemaVentas.repository.ProductRepository;
import com.ventas.SistemaVentas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional // Importante: Si algo falla, no guarda nada (rollback)
    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setPaymentMethod(request.getPaymentMethod());

        // Asignar cajero (Si viene null, podrías asignar uno por defecto o lanzar error)
        if (request.getCashierId() != null) {
            User cashier = userRepository.findById(request.getCashierId()).orElse(null);
            order.setUser(cashier);
        }

        List<OrderItem> items = new ArrayList<>();
        int totalAmount = 0;

        // Recorremos los items que envió el frontend
        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPriceAtPurchase(product.getPrice());
            item.setOrder(order); // Relacionamos con la orden padre

            items.add(item);
            totalAmount += (product.getPrice() * itemRequest.getQuantity());
        }

        order.setItems(items);
        order.setTotal(totalAmount);

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}