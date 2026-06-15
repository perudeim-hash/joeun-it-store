package org.store.joeunit.common.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.store.joeunit.cart.service.CartService;
import org.store.joeunit.member.dto.MemberDto;

@ControllerAdvice
@RequiredArgsConstructor
public class CommonModelAdvice {
    private final CartService cartService;

    @ModelAttribute
    public void addCommonAttributes(Model model, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        Long cartCount = 0L;
        if (loginMember != null) {
            cartCount = cartService.getCartCount(loginMember.getMemberId());
        }
        model.addAttribute("cartCount", cartCount);
    }
}
