package org.store.joeunit.order.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.store.joeunit.member.dto.MemberDto;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.service.OrderService;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderDto orderDto, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) return "fail:login";

        try {
            orderDto.setMemberId(loginMember.getMemberId());
            String membership = loginMember.getMembership();

            int discountRate = getDiscountRate(membership);
            long originalPrice = (orderDto.getTotalPrice() != null) ? orderDto.getTotalPrice() : 0L;
            long discountAmount = (long) (originalPrice * discountRate / 100.0);
            long finalPrice = originalPrice - discountAmount;

            orderDto.setDiscountRate(discountRate);
            orderDto.setDiscountAmount(discountAmount);
            orderDto.setFinalPrice(finalPrice);
            orderDto.setMemberMembership(membership);

            orderService.placeOrderFromCart(loginMember.getMemberId(), orderDto);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            // 서버 에러를 숨기지 않고 프론트로 전송
            return "서버 오류: " + e.getMessage();
        }
    }

    @PostMapping("/delete/{orderId}")
    public String deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrderHistory(orderId);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "서버 오류: " + e.getMessage();
        }
    }

    private int getDiscountRate(String membership) {
        if (membership == null) return 0;
        switch (membership.toUpperCase()) {
            case "VIP": return 30;
            case "GOLD": return 20;
            case "SILVER": return 15;
            case "BRONZE": return 10;
            default: return 0;
        }
    }
}