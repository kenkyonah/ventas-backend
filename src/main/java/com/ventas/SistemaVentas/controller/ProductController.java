package com.ventas.SistemaVentas.controller;

import com.ventas.SistemaVentas.model.Product;
import com.ventas.SistemaVentas.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products") // Ruta base: http://localhost:8080/api/v1/products
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET: Obtener todos los productos (Para el Home y el Admin)
    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    // POST: Crear un producto nuevo (Solo Admin debería usarlo)
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    // PUT: Editar un producto existente
    // Recibe el ID en la URL y los datos nuevos en el cuerpo
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    // DELETE: Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}