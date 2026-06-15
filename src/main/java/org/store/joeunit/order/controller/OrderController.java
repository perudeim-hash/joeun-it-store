package org.store.joeunit.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderDto orderDto) {
        orderService.placeOrderFromCart(orderDto);
        return "success";
    }

    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "success";
    }

    @PostMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrderHistory(orderId);
        return "success";
    }
}