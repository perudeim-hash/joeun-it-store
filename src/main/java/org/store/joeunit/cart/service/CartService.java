package org.store.joeunit.cart.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.joeunit.cart.dao.CartItemDao;
import org.store.joeunit.cart.dto.CartItemDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemDao cartItemDao;

    // 장바구니 목록 출력
    public List<CartItemDto> getCartItems(CartItemDto cartItemDto) {
        List<CartItemDto> cartItems = cartItemDao.findCartItemsByMemberId(cartItemDto);
        return cartItems;
    }

    // 장바구니에서 Item 추가(이미 있는 경우 개수 추가)
    public int addCartItem(CartItemDto cartItemDto) {
        if (cartItemDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상 있어야 합니다.");
        }
        CartItemDto findItem = cartItemDao.findCartItemByMemberIdAndProductId(cartItemDto);
        if (findItem == null) {
            return cartItemDao.createCartItem(cartItemDto);
        }
        return cartItemDao.increaseCartItemQuantity(cartItemDto);
    }

    // 장바구니 업데이트
    public int updateCartItem(CartItemDto cartItemDto) {
        if (cartItemDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상 있어야 합니다.");
        }
        CartItemDto itemDto = CartItemDto.builder()
                .memberId(cartItemDto.getMemberId())
                .cartItemId(cartItemDto.getCartItemId())
                .quantity(cartItemDto.getQuantity())
                .build();

        return cartItemDao.updateCartItemQuantity(itemDto);
    }

    // 장바구니 총 가격
    public int getCartTotalPrice(int memberId) {
        CartItemDto itemDto = CartItemDto.builder()
                .memberId(memberId)
                .build();

        return cartItemDao.cartItemTotalPrice(itemDto);
    }

    //장바구니 아이템 한개 삭제
    public int deleteCartItem(int memberId, int cartItemId) {
        CartItemDto itemDto = CartItemDto.builder()
                .memberId(memberId)
                .cartItemId(cartItemId)
                .build();
        return cartItemDao.deleteCartItem(itemDto);
    }

    //장바구니 전체 삭제
    public int deleteAllCartItem(int memberId) {
        CartItemDto itemDto = CartItemDto.builder()
                .memberId(memberId)
                .build();
        return cartItemDao.deleteCartItemsByMemberId(itemDto);
    }

}
