package com.ventas.SistemaVentas;

import com.ventas.SistemaVentas.model.Product;
import com.ventas.SistemaVentas.repository.ProductRepository;
import com.ventas.SistemaVentas.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

// Usamos Mockito para simular la base de datos (no conectamos a la real en los tests)
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository; // Simulamos el repositorio

    @InjectMocks
    private ProductService productService; // Probamos el servicio

    @Test
    public void testGetAllProducts() {
        // 1. PREPARACIÓN (Given)
        // Creamos un producto falso
        Product prod1 = new Product();
        prod1.setName("Pala");
        prod1.setPrice(1000);

        // Le decimos al mock: "Cuando alguien te pida findAll, devuelve esta lista"
        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(prod1));

        // 2. EJECUCIÓN (When)
        List<Product> result = productService.getAllProducts();

        // 3. VERIFICACIÓN (Then)
        // Verificamos que la lista no esté vacía y tenga el nombre correcto
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Pala", result.get(0).getName());

        System.out.println("Test de Productos: PASADO CORRECTAMENTE ✅");
    }
}