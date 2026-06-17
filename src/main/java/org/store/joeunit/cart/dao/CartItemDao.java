package org.store.joeunit.cart.dao;

import org.apache.ibatis.annotations.Mapper;
import org.store.joeunit.cart.dto.CartItemDto;

import java.util.List;

@Mapper
public interface CartItemDao {
    List<CartItemDto> findCartItemsByMemberId(CartItemDto cartItemDto);

    CartItemDto findCartItemByMemberIdAndProductId(CartItemDto cartItemDto);

    CartItemDto findCartItemByMemberIdAndCartItemId(CartItemDto cartItemDto);

    Long cartItemTotalPrice(CartItemDto cartItemDto);

    Long createCartItem(CartItemDto cartItemDto);

    Long increaseCartItemQuantity(CartItemDto cartItemDto);

    Long updateCartItemQuantity(CartItemDto cartItemDto);

    Long deleteCartItem(CartItemDto cartItemDto);

    Long deleteCartItemsByMemberId(CartItemDto cartItemDto);

    Long countCartItemsByMemberId(Long memberId);
}

