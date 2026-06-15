package org.store.joeunit.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.dto.OrderItemDto;
import java.util.List;

@Mapper
public interface OrderMapper {
    void insertOrder(OrderDto orderDto);
    void insertOrderItem(OrderItemDto orderItemDto);

    // Cart 오류 우회를 위해 productId 대신 productName으로 장바구니를 비웁니다.
    void deleteCartItemAfterOrder(@Param("memberId") Long memberId, @Param("productName") String productName);

    List<OrderDto> selectMyOrderList(Long memberId);
    OrderDto selectOrderDetail(Long orderId);
    List<OrderItemDto> selectOrderItemsByOrderId(Long orderId);
    void updateOrderStatusToCancel(Long orderId);
    void deleteOrderItems(Long orderId);
    void deleteOrder(Long orderId);
}