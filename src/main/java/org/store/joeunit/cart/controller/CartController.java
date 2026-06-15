package org.store.joeunit.cart.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.store.joeunit.cart.dto.CartItemDto;
import org.store.joeunit.cart.service.CartService;
import org.store.joeunit.member.dto.MemberDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/cart")
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    //아이템 리스트 출력
    @GetMapping("/carts")
    public String cartList(Model model, HttpSession session) {

        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        Long memberId = loginMember.getMemberId();

        CartItemDto cartItemDto = CartItemDto.builder()
                .memberId(memberId)
                .build();
        List<CartItemDto> cartItems = cartService.getCartItems(cartItemDto);
        Long cartTotalPrice = cartService.getCartTotalPrice(memberId);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotalPrice", cartTotalPrice);
        return "cart/carts";
    }

    @PostMapping("/update")
    public String cartItemUpdate(CartItemDto cartItemDto, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        cartItemDto.setMemberId(loginMember.getMemberId());
        cartService.updateCartItem(cartItemDto);
        return "redirect:/cart/carts";
    }

    @PostMapping("/update-ajax")
    @ResponseBody
    public Map<String, Object> cartItemUpdateAjax(CartItemDto cartItemDto, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return response;
        }

        cartItemDto.setMemberId(loginMember.getMemberId());

        Long result = cartService.updateCartItem(cartItemDto);
        Long cartTotalPrice = cartService.getCartTotalPrice(loginMember.getMemberId());

        response.put("success", result > 0);
        response.put("cartItemId", cartItemDto.getCartItemId());
        response.put("quantity", cartItemDto.getQuantity());
        response.put("cartTotalPrice", cartTotalPrice);

        return response;
    }

    @PostMapping("/add")
    public String cartItemAdd(CartItemDto cartItemDto, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        cartItemDto.setMemberId(loginMember.getMemberId());
        cartService.addCartItem(cartItemDto);
        return "redirect:/cart/carts";
    }


    // 아이텝 삭제
    @PostMapping("/delete")
    public String cartItemDelete(@RequestParam int cartItemId, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        cartService.deleteCartItem(loginMember.getMemberId(), cartItemId);
        return "redirect:/cart/carts";
    }

    //장바구니 전체 삭제
    @PostMapping("/delete-all")
    public String cartItemDeleteAll(HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        cartService.deleteAllCartItem(loginMember.getMemberId());
        return "redirect:/cart/carts";
    }
}
