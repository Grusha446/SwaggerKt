package ru.ithub.spring.kt1java;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderMain.class)
class OrderMainTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Orders sampleOrder;

    @BeforeEach
    void setUp() {
        sampleOrder = new Orders();
        sampleOrder.setId(1L);
        sampleOrder.setProduct("Test Product");
        sampleOrder.setQuantity(5);
        sampleOrder.setPrice(BigDecimal.valueOf(100.00));
        sampleOrder.setStatus(OrdersStatus.CREATED);
    }

    @Test
    void createOrder_ValidOrder_ShouldReturnCreatedOrder() throws Exception {
        when(orderService.createOrder(any(Orders.class))).thenReturn(sampleOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleOrder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.product").value("Test Product"));
    }

    @Test
    void getAllOrders_ShouldReturnOrderList() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(sampleOrder));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].product").value("Test Product"));
    }

    @Test
    void getOrderById_ExistingOrder_ShouldReturnOrder() throws Exception {
        when(orderService.getOrderByID(1L)).thenReturn(Optional.of(sampleOrder));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.product").value("Test Product"));
    }

    @Test
    void getOrderById_NonExistingOrder_ShouldReturnNotFound() throws Exception {
        when(orderService.getOrderByID(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/orders/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateOrder_ValidOrder_ShouldReturnUpdatedOrder() throws Exception {
        when(orderService.updateOrder(eq(1L), any(Orders.class))).thenReturn(sampleOrder);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("Test Product"));
    }

    @Test
    void deleteOrder_ShouldReturnNoContent() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }
}