package org.store.joeunit.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.store.joeunit.order.service.OrderService;

@Controller
public class OrderViewController {

    @Autowired
    private OrderService orderService;

    // 1. 주문서 작성 페이지 열기
    @GetMapping("/order/form")
    public String orderForm() {
        return "order/order_form"; // templates/order/order_form.html 파일을 엽니다.
    }

    // 2. 내 주문 내역 페이지 열기
    @GetMapping("/order/list")
    public String orderList(Model model) {
        // 에러 원인 해결: getMyOrders -> getOrderList 로 변경!
        // 회원번호 2번(user1 테스트회원)의 주문 내역을 가져옵니다.
        model.addAttribute("orders", orderService.getOrderList(2L));
        return "order/order_list"; // templates/order/order_list.html 파일을 엽니다.
    }
}