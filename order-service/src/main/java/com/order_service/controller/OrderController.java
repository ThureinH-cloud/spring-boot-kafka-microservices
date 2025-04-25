package com.order_service.controller;

import com.central.dto.Order;
import com.central.dto.OrderEvent;
import com.order_service.kafka.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    private OrderProducer orderProducer;
    @PostMapping("/orders")
    private String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent event = new OrderEvent();
        event.setStatus("PENDING");
        event.setMessage("Order status is in pending state.");
        event.setOrder(order);
        orderProducer.sendOrderEvent(event);
        return "Order placed successfully";
    }
}
