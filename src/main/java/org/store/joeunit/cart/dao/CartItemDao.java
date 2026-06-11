package org.store.joeunit.cart.dao;

import org.store.joeunit.cart.dto.CartItemDto;

public interface CartItemDao {
    CartItemDto findCartItemsByMemberId(CartItemDto cartItemDto);

    CartItemDto findCartItemByMemberIdAndProductId(CartItemDto cartItemDto);

    CartItemDto createCartItem(CartItemDto cartItemDto);

    CartItemDto increaseCartItemQuantity(CartItemDto cartItemDto);

    CartItemDto updateCartItemQuantity(CartItemDto cartItemDto);

    CartItemDto deleteCartItem(CartItemDto cartItemDto);

    CartItemDto deleteCartItemsByMemberId(CartItemDto cartItemDto);




}

