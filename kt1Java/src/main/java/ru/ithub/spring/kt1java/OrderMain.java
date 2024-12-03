package ru.ithub.spring.kt1java;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderMain {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Создать новый заказ", description = "Создает новый заказ и возвращает его")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Создан заказ"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос")
    })
    @PostMapping
    public ResponseEntity<Orders> createOrder(@Valid @RequestBody Orders orders) {
        Orders createOrder = orderService.createOrder(orders);
        return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
    }

    @Operation(summary = "Получить все заказы", description = "Возвращает список всех заказов")
    @ApiResponse(responseCode = "200", description = "Список заказов")
    @GetMapping
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Operation(summary = "Получить заказ по ID", description = "Возвращает заказ по указанному ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ найден"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        return orderService.getOrderByID(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Operation(summary = "Обновить заказ", description = "Обновляет информацию о заказе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ обновлен"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @Valid @RequestBody Orders ordersDetails) {
        Orders updateOrder = orderService.updateOrder(id, ordersDetails);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

    @Operation(summary = "Удалить заказ", description = "Удаляет заказ по указанному ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Заказ удален"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
