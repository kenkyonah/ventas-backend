package com.ventas.SistemaVentas.service;

import com.ventas.SistemaVentas.model.Product;
import com.ventas.SistemaVentas.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica a Spring que esta clase contiene la lógica de negocio
public class ProductService {

    @Autowired
    private ProductRepository productRepository; // Inyectamos el repositorio para hablar con la BD

    /**
     * Obtener todos los productos.
     * Se usa para listar en el Home y en el Panel de Admin.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Buscar un producto específico por su ID.
     * Útil para validar existencia antes de operaciones.
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Guardar un producto nuevo en la base de datos.
     * Se usa cuando el Admin crea un producto en el formulario.
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Actualizar un producto existente.
     * 1. Busca el producto por ID.
     * 2. Si existe, actualiza sus campos con los nuevos datos.
     * 3. Guarda los cambios.
     */
    public Product updateProduct(Long id, Product productDetails) {
        // Buscamos el producto, si no existe lanzamos un error (RuntimeExcepcion)
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));

        // Actualizamos los campos
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());
        product.setImageUrl(productDetails.getImageUrl());
        product.setCategoryId(productDetails.getCategoryId());

        // Guardamos el producto actualizado
        return productRepository.save(product);
    }

    /**
     * Eliminar un producto de la base de datos por su ID.
     * Se usa desde el botón de "Papelera" en el Admin.
     */
    public void deleteProduct(Long id) {
        // Spring Data JPA se encarga de la query DELETE internamente
        productRepository.deleteById(id);
    }
}