package org.store.joeunit.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.service.OrderService;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. [C] 장바구니 주문 처리
    @PostMapping("/create")
    public String createOrder(@RequestBody OrderDto orderDto) {
        if(orderDto.getMemberId() == null) orderDto.setMemberId(2L); // user1(테스트회원) 강제 세팅
        orderService.placeOrderFromCart(orderDto);
        return "주문이 성공적으로 완료되었으며 장바구니에서 삭제되었습니다.";
    }

    // 2. [R] 주문 목록
    @GetMapping("/list/{memberId}")
    public List<OrderDto> getOrderList(@PathVariable Long memberId) {
        return orderService.getOrderList(memberId);
    }

    // 3. [U] 주문 상세 내용
    @GetMapping("/detail/{orderId}")
    public OrderDto getOrderDetail(@PathVariable Long orderId) {
        return orderService.getOrderDetail(orderId);
    }

    // 4. [D] 주문 취소
    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return "주문이 취소(ORDER_CANCEL) 되었습니다.";
    }
    // 5. [D] 주문 내역 완전 삭제
    @PostMapping("/delete/{orderId}")
    public String deleteOrderHistory(@PathVariable Long orderId) {
        orderService.deleteOrderHistory(orderId);
        return "주문 내역이 성공적으로 완전 삭제되었습니다.";
    }
}