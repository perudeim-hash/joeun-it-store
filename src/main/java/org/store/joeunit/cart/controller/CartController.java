package org.store.joeunit.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.store.joeunit.cart.dto.CartItemDto;
import org.store.joeunit.cart.service.CartService;

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
    public String cartList(Model model) {
        int memberId = 2;
        CartItemDto cartItemDto = CartItemDto.builder()
                .memberId(memberId)
                .build();
        List<CartItemDto> cartItems = cartService.getCartItems(cartItemDto);
        int cartTotalPrice = cartService.getCartTotalPrice(memberId);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotalPrice", cartTotalPrice);
        return "cart/carts";
    }

    @PostMapping("/update")
    public String cartItemUpdate( CartItemDto cartItemDto) {
        int memberId = 2;
        cartItemDto.setMemberId(memberId);
        cartService.updateCartItem(cartItemDto);
        return "redirect:/cart/carts";
    }

    @PostMapping("/update-ajax")
    @ResponseBody
    public Map<String, Object> cartItemUpdateAjax(CartItemDto cartItemDto) {

        int memberId = 2;
        cartItemDto.setMemberId(memberId);

        int result = cartService.updateCartItem(cartItemDto);
        int cartTotalPrice = cartService.getCartTotalPrice(memberId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", result > 0);
        response.put("cartItemId", cartItemDto.getCartItemId());
        response.put("quantity", cartItemDto.getQuantity());
        response.put("cartTotalPrice", cartTotalPrice);

        return response;
    }

    @PostMapping("/add")
    public String cartItemAdd(CartItemDto cartItemDto) {
        int memberId = 2;
        cartItemDto.setMemberId(memberId);
        cartService.addCartItem(cartItemDto);
        return "redirect:/cart/carts";
    }


    // 아이텝 삭제
    @PostMapping("/delete")
    public String cartItemDelete(@RequestParam int cartItemId) {
        int memberId = 2;
        cartService.deleteCartItem(memberId, cartItemId);
        return "redirect:/cart/carts";
    }
    //장바구니 전체 삭제
    @PostMapping("/delete-all")
    public String cartItemDeleteAll() {
        int memberId = 2;
        cartService.deleteAllCartItem(memberId);
        return "redirect:/cart/carts";
    }
}
