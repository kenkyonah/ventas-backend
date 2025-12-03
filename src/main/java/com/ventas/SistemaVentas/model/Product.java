package com.ventas.SistemaVentas.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Marca la clase como una Tabla de Base de Datos
@Table(name = "products") // Nombre de la tabla en MySQL
@Data // Lombok: Crea Getters, Setters, toString, etc. automáticamente
public class Product {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremental (1, 2, 3...)
    private Long id;

    @Column(nullable = false) // Campo obligatorio (NOT NULL)
    private String name;

    @Column(columnDefinition = "TEXT") // Permite textos largos en la BD
    private String description;

    @Column(nullable = false) // Precio obligatorio
    private Integer price;

    private String imageUrl; // URL de la imagen (opcional)

    @Column(nullable = false) // ID de la categoría obligatorio
    private Integer categoryId;
}