package org.store.joeunit.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.joeunit.cart.dto.CartItemDto;
import org.store.joeunit.cart.service.CartService;
import org.store.joeunit.member.service.MemberService;
import org.store.joeunit.member.dto.MemberDto;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.dto.OrderItemDto;
import org.store.joeunit.order.mapper.OrderMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final CartService cartService;
    private final MemberService memberService;

    public List<OrderDto> getOrderList(Long memberId) {
        return orderMapper.selectMyOrderList(memberId);
    }

    public OrderDto getOrderDetail(Long orderId) {
        OrderDto order = orderMapper.selectOrderDetail(orderId);
        if (order != null) {
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

        // ✨ 반드시 '할인이 적용된 최종 결제 금액(FinalPrice)'으로 누적되도록 전달!
        memberService.updateMembership(memberId, orderDto.getFinalPrice());

        for (CartItemDto item : cartItems) {
            Long safeOrderPrice = Long.parseLong(String.valueOf(item.getPrice()));
            Long safeTotalPrice = Long.parseLong(String.valueOf(item.getItemTotalPrice()));

            OrderItemDto orderItem = OrderItemDto.builder()
                    .orderId(orderDto.getOrderId())
                    .productId(0L)
                    .productName(item.getProductName())
                    .orderPrice(safeOrderPrice)
                    .quantity(item.getQuantity())
                    .itemTotalPrice(safeTotalPrice)
                    .build();

            orderMapper.insertOrderItem(orderItem);
            orderMapper.deleteCartItemAfterOrder(memberId, item.getProductName());
        }
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        OrderDto order =
        orderMapper.selectOrderDetail(orderId);

        orderMapper.updateOrderStatusToCancel(orderId);

        Long totalspent =
                orderMapper.selectTotalSpentByMember(
                        order.getMemberId()
                );

        memberService.updateMembershipByTotal(
                order.getMemberId(),
                totalspent
        );
    }

    @Transactional
    public void deleteOrderHistory(Long orderId) {
        orderMapper.deleteOrderItems(orderId);
        orderMapper.deleteOrder(orderId);
    }

    public long getTotalSpent(Long memberId) {
        return orderMapper.selectTotalSpentByMember(memberId);
    }
}