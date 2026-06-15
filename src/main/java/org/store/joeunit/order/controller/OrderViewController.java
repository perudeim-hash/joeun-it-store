package org.store.joeunit.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.store.joeunit.order.service.OrderService;

@Controller
public class OrderViewController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/order/form")
    public String orderForm() { return "order/order_form"; }

    @GetMapping("/order/list")
    public String orderList(Model model) {
        model.addAttribute("orders", orderService.getOrderList(2L));
        return "order/order_list";
    }

    @GetMapping("/order/detail/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderDetail(orderId));
        return "order/order_detail";
    }
}