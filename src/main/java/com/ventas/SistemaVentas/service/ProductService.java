package com.ventas.SistemaVentas.service;

import com.ventas.SistemaVentas.model.Product;
import com.ventas.SistemaVentas.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Buscar por ID (Para editar)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    //NUEVO: Eliminar producto
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Actualizar producto
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());
        product.setImageUrl(productDetails.getImageUrl());
        product.setCategoryId(productDetails.getCategoryId());

        return productRepository.save(product);
    }
}