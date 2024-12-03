package ru.ithub.spring.kt1java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class   OrderService {

    @Autowired
    private OrderInterface orderInterface;

    public Orders createOrder(Orders orders)
    {
        orders.setOrderDate(LocalDate.now());
        return orderInterface.save(orders);
    }

    public List<Orders> getAllOrders()
    {
        return orderInterface.findAll();
    }

    public Optional<Orders> getOrderByID(long id)
    {
        return orderInterface.findById(id);
    }

    public void deleteOrder(Long id)
    {
        orderInterface.deleteById(id);
    }

    public void deleteAllOrder()
    {
        orderInterface.deleteAll();
    }

    public Orders updateOrder(Long id, Orders orderDetails)
    {
        Orders orders = orderInterface.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orders.setProduct(orderDetails.getProduct());
        orders.setQuantity(orderDetails.getQuantity());
        orders.setPrice(orderDetails.getPrice());
        orders.setStatus(orderDetails.getStatus());
        return  orderInterface.save(orders);
    }
}
