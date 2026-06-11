package org.store.joeunit.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.store.joeunit.cart.dto.CartItemDto;
import org.store.joeunit.cart.service.CartService;

import java.util.List;

@RequestMapping("/cart")
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

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

    @PostMapping("/delete")
    public String CartItemDeleteAll(int cartItemId) {
        int memberId = 2;

        cartService.deleteCartItem(memberId,cartItemId);
        return "redirect:/cart/carts";
    }

    @PostMapping("/delete-all")
    public String CartItemDeleteAll() {
        int memberId = 2;
        cartService.deleteAllCartItem(memberId);
        return "redirect:/cart/carts";
    }
}
