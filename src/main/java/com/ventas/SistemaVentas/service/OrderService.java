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

    // @Transactional asegura que si algo falla, no se guarde una orden a medias
    @Transactional
    public Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setDate(LocalDateTime.now()); // Fecha actual
        order.setPaymentMethod(request.getPaymentMethod()); // Medio de pago

        // Asignar el cajero que hizo la venta
        if (request.getCashierId() != null) {
            User cashier = userRepository.findById(request.getCashierId()).orElse(null);
            order.setUser(cashier);
        }

        List<OrderItem> items = new ArrayList<>();
        int totalAmount = 0;

        // Recorremos cada producto del carrito
        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            // Buscamos el producto en la BD para obtener su precio real
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemRequest.getProductId()));

            // Creamos el detalle de la venta
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPriceAtPurchase(product.getPrice()); // Guardamos el precio histórico
            item.setOrder(order);

            items.add(item);
            // Sumamos al total: Precio * Cantidad
            totalAmount += (product.getPrice() * itemRequest.getQuantity());
        }

        order.setItems(items);
        order.setTotal(totalAmount);

        // Guardamos la orden completa en la BD
        return orderRepository.save(order);
    }

    // Obtener historial completo para reportes
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}