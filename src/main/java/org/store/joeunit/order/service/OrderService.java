package org.store.joeunit.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.joeunit.cart.dto.CartItemDto;
import org.store.joeunit.cart.service.CartService;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.dto.OrderItemDto;
import org.store.joeunit.order.mapper.OrderMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final CartService cartService;

    public List<OrderDto> getOrderList(Long memberId) {
        return orderMapper.selectMyOrderList(memberId);
    }

    public OrderDto getOrderDetail(Long orderId) {
        OrderDto order = orderMapper.selectOrderDetail(orderId);
        if(order != null) {
            order.setOrderItems(orderMapper.selectOrderItemsByOrderId(orderId));
        }
        return order;
    }

    @Transactional
    public void placeOrderFromCart(Long memberId, OrderDto orderDto) {
        List<CartItemDto> cartItems = cartService.getCartItems(CartItemDto.builder().memberId(memberId).build());

        if (orderDto.getTotalPrice() == null) orderDto.setTotalPrice(0L);
        if (orderDto.getFinalPrice() == null) orderDto.setFinalPrice(orderDto.getTotalPrice());
        if (orderDto.getDiscountAmount() == null) orderDto.setDiscountAmount(0L);

        orderMapper.insertOrder(orderDto);

        for (CartItemDto item : cartItems) {
            // 어떤 숫자 타입(int/Long)이 와도 에러가 나지 않도록 안전하게 변환
            Long safeOrderPrice = Long.parseLong(String.valueOf(item.getPrice()));
            Long safeTotalPrice = Long.parseLong(String.valueOf(item.getItemTotalPrice()));

            OrderItemDto orderItem = OrderItemDto.builder()
                    .orderId(orderDto.getOrderId())
                    // XML 쿼리에서 이름을 통해 직접 번호를 찾을 것이므로 0으로 둡니다.
                    .productId(0L)
                    .productName(item.getProductName())
                    .orderPrice(safeOrderPrice)
                    .quantity(item.getQuantity())
                    .itemTotalPrice(safeTotalPrice)
                    .build();

            orderMapper.insertOrderItem(orderItem);

            // Cart 쪽 수정을 막기 위해, 삭제할 때도 "상품 이름"을 넘겨줍니다.
            orderMapper.deleteCartItemAfterOrder(memberId, item.getProductName());
        }
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        orderMapper.updateOrderStatusToCancel(orderId);
    }

    @Transactional
    public void deleteOrderHistory(Long orderId) {
        orderMapper.deleteOrderItems(orderId);
        orderMapper.deleteOrder(orderId);
    }
}