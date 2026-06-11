package org.store.joeunit.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.service.OrderService;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. 주문 완료 처리 요청 (JSON 형식으로 주문 데이터를 받아옴)
    @PostMapping("/api/order")
    public String createOrder(@RequestBody OrderDto orderDto) {
        try {
            // 임시 테스트용 회원 ID 세팅 (실제 구현 시 세션 등에서 가져옴)
            if(orderDto.getMemberId() == null) {
                orderDto.setMemberId(1L);
            }
            orderService.placeOrder(orderDto);
            return "주문이 완료되었습니다.";
        } catch (Exception e) {
            e.printStackTrace();
            return "주문 처리 중 오류 발생: " + e.getMessage();
        }
    }

    // 2. 내 주문 내역 전체 조회 요청
    @GetMapping("/api/order/my")
    public List<OrderDto> myOrderList(@RequestParam(value = "memberId", defaultValue = "1") Long memberId) {
        return orderService.getMyOrders(memberId);
    }
}