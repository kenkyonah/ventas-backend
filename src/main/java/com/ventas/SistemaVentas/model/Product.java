package com.ventas.SistemaVentas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity // Marca la clase como una Tabla de Base de Datos
@Table(name = "products") // Nombre de la tabla en MySQL
@Data // Lombok: Crea Getters, Setters, toString, etc. automáticamente
public class Product {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental (1, 2, 3...)
    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 10, message = "El precio debe ser mayor a 10")
    @Column(nullable = false)
    private Integer price;

    private String imageUrl; // URL de la imagen (opcional)

    @Column(nullable = false) // ID de la categoría obligatorio
    private Integer categoryId;
}