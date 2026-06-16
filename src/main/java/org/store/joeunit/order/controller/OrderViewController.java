package org.store.joeunit.order.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.store.joeunit.cart.dto.CartItemDto;
import org.store.joeunit.cart.service.CartService;
import org.store.joeunit.member.dto.MemberDto;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.service.OrderService;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderViewController {

    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/form")
    public String orderForm(Model model, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) return "redirect:/member/login";

        List<CartItemDto> cartItems = cartService.getCartItems(CartItemDto.builder().memberId(loginMember.getMemberId()).build());
        Long totalPrice = cartService.getCartTotalPrice(loginMember.getMemberId());
        if (totalPrice == null) totalPrice = 0L;

        String grade = loginMember.getMembership();
        int discountRate = 0;
        String memberGradeName = "일반";

        if (grade != null) {
            switch (grade.toUpperCase()) {
                case "VIP": discountRate = 30; memberGradeName = "VIP"; break;
                case "GOLD": discountRate = 20; memberGradeName = "골드"; break;
                case "SILVER": discountRate = 15; memberGradeName = "실버"; break;
                case "BRONZE": discountRate = 10; memberGradeName = "브론즈"; break;
                default: discountRate = 0; memberGradeName = "일반";
            }
        }

        long discountAmount = (long) (totalPrice * (discountRate / 100.0));
        long finalPrice = totalPrice - discountAmount;

        model.addAttribute("orderItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("discountRate", discountRate);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("memberGradeName", memberGradeName);

        return "order/order_form";
    }

    @GetMapping("/list")
    public String orderList(Model model, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) return "redirect:/member/login";

        model.addAttribute("orders", orderService.getOrderList(loginMember.getMemberId()));
        return "order/order_list";
    }

    // [신규 추가] 주문 상세 페이지 이동 로직
    @GetMapping("/detail/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) return "redirect:/member/login";

        OrderDto order = orderService.getOrderDetail(orderId);

        // 주문 내역이 없거나 본인 주문이 아니면 목록으로 튕겨냄 (보안)
        if (order == null || !order.getMemberId().equals(loginMember.getMemberId())) {
            return "redirect:/order/list";
        }

        model.addAttribute("order", order);
        return "order/order_detail";
    }
}