package ru.ithub.spring.kt1java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderInterface orderInterface;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_ShouldSaveOrder() {
        // Arrange
        Orders order = new Orders();
        order.setProduct("Test Product");
        order.setQuantity(5);
        order.setPrice(BigDecimal.valueOf(100.00));
        order.setStatus(OrdersStatus.CREATED);

        when(orderInterface.save(order)).thenReturn(order);

        // Act
        Orders createdOrder = orderService.createOrder(order);

        // Assert
        assertNotNull(createdOrder);
        assertEquals("Test Product", createdOrder.getProduct());
        verify(orderInterface, times(1)).save(order);
    }

    @Test
    void getAllOrders_ShouldReturnOrderList() {
        // Arrange
        Orders order1 = new Orders();
        Orders order2 = new Orders();
        List<Orders> expectedOrders = Arrays.asList(order1, order2);

        when(orderInterface.findAll()).thenReturn(expectedOrders);

        // Act
        List<Orders> actualOrders = orderService.getAllOrders();

        // Assert
        assertEquals(2, actualOrders.size());
        verify(orderInterface, times(1)).findAll();
    }

    @Test
    void getOrderById_ExistingOrder_ShouldReturnOrder() {
        // Arrange
        Long orderId = 1L;
        Orders expectedOrder = new Orders();
        expectedOrder.setId(orderId);
        expectedOrder.setProduct("Test Product");

        when(orderInterface.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        // Act
        Optional<Orders> foundOrder = orderService.getOrderByID(orderId);

        // Assert
        assertTrue(foundOrder.isPresent());
        assertEquals(orderId, foundOrder.get().getId());
        verify(orderInterface, times(1)).findById(orderId);
    }
}