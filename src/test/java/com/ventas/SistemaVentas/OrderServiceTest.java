package com.ventas.SistemaVentas;

import com.ventas.SistemaVentas.dto.OrderRequest;
import com.ventas.SistemaVentas.model.Order;
import com.ventas.SistemaVentas.model.Product;
import com.ventas.SistemaVentas.model.User;
import com.ventas.SistemaVentas.repository.OrderRepository;
import com.ventas.SistemaVentas.repository.ProductRepository;
import com.ventas.SistemaVentas.repository.UserRepository;
import com.ventas.SistemaVentas.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateOrder_CalculaTotalCorrectamente() {
        // 1. DATOS DE PRUEBA (GIVEN)
        // Simulamos un cajero
        User cajero = new User();
        cajero.setId(1L);
        cajero.setUsername("cajero1");

        // Simulamos un producto (Precio: 5000)
        Product pala = new Product();
        pala.setId(100L);
        pala.setName("Pala");
        pala.setPrice(5000);

        // Simulamos la petición del frontend (2 Palas)
        OrderRequest request = new OrderRequest();
        request.setCashierId(1L);
        request.setPaymentMethod("EFECTIVO");

        OrderRequest.OrderItemRequest itemReq = new OrderRequest.OrderItemRequest();
        itemReq.setProductId(100L);
        itemReq.setQuantity(2); // Compramos 2 unidades
        request.setItems(List.of(itemReq));

        // 2. COMPORTAMIENTO ESPERADO (MOCKS)
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(cajero));
        Mockito.when(productRepository.findById(100L)).thenReturn(Optional.of(pala));

        // Cuando guarde, devolvemos la orden tal cual entra para verificarla
        Mockito.when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        // 3. EJECUCIÓN (WHEN)
        Order nuevaOrden = orderService.createOrder(request);

        // 4. VERIFICACIÓN (THEN)
        Assertions.assertNotNull(nuevaOrden);
        // Validamos que 2 * 5000 = 10000
        Assertions.assertEquals(10000, nuevaOrden.getTotal());
        Assertions.assertEquals("EFECTIVO", nuevaOrden.getPaymentMethod());
        Assertions.assertEquals(1, nuevaOrden.getItems().size());

        System.out.println("✅ Test Venta: CÁLCULO DE TOTAL (5000 x 2 = 10000) CORRECTO");
    }
}