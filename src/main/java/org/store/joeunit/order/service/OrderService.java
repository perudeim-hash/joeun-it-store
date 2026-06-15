package org.store.joeunit.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.dto.OrderItemDto;
import org.store.joeunit.order.mapper.OrderMapper;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public List<OrderDto> getOrderList(Long memberId) { return orderMapper.selectMyOrderList(memberId); }

    public OrderDto getOrderDetail(Long orderId) {
        OrderDto order = orderMapper.selectOrderDetail(orderId);
        if(order != null) order.setOrderItems(orderMapper.selectOrderItemsByOrderId(orderId));
        return order;
    }

    @Transactional
    public void placeOrderFromCart(OrderDto orderDto) {
        orderMapper.insertOrder(orderDto);
        for (OrderItemDto item : orderDto.getOrderItems()) {
            item.setOrderId(orderDto.getOrderId());
            orderMapper.insertOrderItem(item);
            orderMapper.deleteCartItemAfterOrder(orderDto.getMemberId(), item.getProductId());
        }
    }

    @Transactional
    public void cancelOrder(Long orderId) { orderMapper.updateOrderStatusToCancel(orderId); }

    @Transactional
    public void deleteOrderHistory(Long orderId) {
        orderMapper.deleteOrderItems(orderId);
        orderMapper.deleteOrder(orderId);
    }
}