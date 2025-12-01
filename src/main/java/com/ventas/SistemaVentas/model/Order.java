package com.ventas.SistemaVentas.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders") // "order" es palabra reservada en SQL, usamos "orders"
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private Integer total;

    private String paymentMethod; // EFECTIVO, DEBITO, CREDITO

    // Relación con el Usuario (Vendedor/Cajero)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Relación con los detalles (Una orden tiene muchos items)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}