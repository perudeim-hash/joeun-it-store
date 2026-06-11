package org.store.joeunit.cart.dao;

import org.apache.ibatis.annotations.Mapper;
import org.store.joeunit.cart.dto.CartItemDto;

import java.util.List;

@Mapper
public interface CartItemDao {
    List<CartItemDto> findCartItemsByMemberId(CartItemDto cartItemDto);

    CartItemDto findCartItemByMemberIdAndProductId(CartItemDto cartItemDto);

    int cartItemTotalPrice(CartItemDto cartItemDto);

    int createCartItem(CartItemDto cartItemDto);

    int increaseCartItemQuantity(CartItemDto cartItemDto);

    int updateCartItemQuantity(CartItemDto cartItemDto);

    int deleteCartItem(CartItemDto cartItemDto);

    int deleteCartItemsByMemberId(CartItemDto cartItemDto);


}

