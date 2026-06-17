package org.store.joeunit.cart.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.joeunit.cart.dao.CartItemDao;
import org.store.joeunit.cart.dto.CartItemDto;
import org.store.joeunit.product.service.ProductService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductService productService;


    // 장바구니 목록 출력
    public List<CartItemDto> getCartItems(CartItemDto cartItemDto) {
        List<CartItemDto> cartItems = cartItemDao.findCartItemsByMemberId(cartItemDto);
        return cartItems;
    }

    // 장바구니에서 Item 추가(이미 있는 경우 개수 추가)
    public Long addCartItem(CartItemDto cartItemDto) {
        if (cartItemDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상 있어야 합니다.");
        }
        int productUpdateResult = productService.increaseSalesAndDecreaseStock(cartItemDto.getProductId(), cartItemDto.getQuantity());

        if (productUpdateResult == 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        CartItemDto findItem = cartItemDao.findCartItemByMemberIdAndProductId(cartItemDto);
        if (findItem == null) {
            return cartItemDao.createCartItem(cartItemDto);
        }
        return cartItemDao.increaseCartItemQuantity(cartItemDto);
    }

    // 장바구니 업데이트
    public Long updateCartItem(CartItemDto cartItemDto) {
        if (cartItemDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상 있어야 합니다.");
        }
        CartItemDto findItem = cartItemDao.findCartItemByMemberIdAndCartItemId(cartItemDto);
        if (findItem == null) {
            throw new IllegalArgumentException("장바구니 상품을 찾을 수 없습니다.");
        }
        int oldQuantity = findItem.getQuantity();
        int newQuantity = cartItemDto.getQuantity();
        int diffQuantity = newQuantity - oldQuantity;

        if (diffQuantity > 0) {
            int productUpdateResult = productService.increaseSalesAndDecreaseStock(findItem.getProductId(), diffQuantity);

            if (productUpdateResult == 0) {
                throw new IllegalArgumentException("재고가 부족합니다.");
            }
        }

        if (diffQuantity < 0) {
            productService.decreaseSalesAndIncreaseStock(findItem.getProductId(), Math.abs(diffQuantity));

        }


        CartItemDto itemDto = CartItemDto.builder()
                .memberId(cartItemDto.getMemberId())
                .cartItemId(cartItemDto.getCartItemId())
                .quantity(cartItemDto.getQuantity())
                .build();

        return cartItemDao.updateCartItemQuantity(itemDto);
    }

    // 장바구니 총 가격
    public Long getCartTotalPrice(Long memberId) {
        CartItemDto itemDto = CartItemDto.builder()
                .memberId(memberId)
                .build();

        return cartItemDao.cartItemTotalPrice(itemDto);
    }

    //장바구니 아이템 한개 삭제
    public Long deleteCartItem(Long memberId, int cartItemId) {
        CartItemDto itemDto = CartItemDto.builder()
                .memberId(memberId)
                .cartItemId(cartItemId)
                .build();

        CartItemDto findItem = cartItemDao.findCartItemByMemberIdAndCartItemId(itemDto);
        if (findItem == null) {
            throw new IllegalArgumentException("장바구니 상품을 찾을 수 없습니다.");
        }
        productService.decreaseSalesAndIncreaseStock(findItem.getProductId(), findItem.getQuantity());

        return cartItemDao.deleteCartItem(itemDto);
    }

    //장바구니 전체 삭제
    public Long deleteAllCartItem(Long memberId) {
        CartItemDto itemDto = CartItemDto.builder()
                .memberId(memberId)
                .build();

        List<CartItemDto> cartItem = cartItemDao.findCartItemsByMemberId(itemDto);
        for (CartItemDto item : cartItem) {
            productService.decreaseSalesAndIncreaseStock(item.getProductId(), item.getQuantity());
        }
        return cartItemDao.deleteCartItemsByMemberId(itemDto);
    }

    public Long getCartCount(Long memberId) {
        return cartItemDao.countCartItemsByMemberId(memberId);
    }

}
