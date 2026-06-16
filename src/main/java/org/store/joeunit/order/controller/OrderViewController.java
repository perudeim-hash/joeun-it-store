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

        // 브론즈 1%, 실버 3%, 골드 5%, VIP 10% 등급 할인율 적용
        int discountRate = 1;
        String memberGradeName = "BRONZE";

        if (grade != null) {
            switch (grade.toUpperCase()) {
                case "VIP": discountRate = 10; memberGradeName = "VIP"; break;
                case "GOLD": discountRate = 5; memberGradeName = "GOLD"; break;
                case "SILVER": discountRate = 3; memberGradeName = "SILVER"; break;
                case "BRONZE": discountRate = 1; memberGradeName = "BRONZE"; break;
                default: discountRate = 1; memberGradeName = "BRONZE";
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

        // ✨ [추가] 하단 UI에 표시할 나의 누적 결제 금액(취소 제외) 데이터를 모델에 추가 ✨
        long totalSpent = orderService.getTotalSpent(loginMember.getMemberId());
        model.addAttribute("totalSpent", totalSpent);

        return "order/order_list";
    }

    @GetMapping("/detail/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) return "redirect:/member/login";

        OrderDto order = orderService.getOrderDetail(orderId);

        if (order == null || !order.getMemberId().equals(loginMember.getMemberId())) {
            return "redirect:/order/list";
        }

        model.addAttribute("order", order);
        return "order/order_detail";
    }
}