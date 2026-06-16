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
    void deleteCartItemAfterOrder(@Param("memberId") Long memberId, @Param("productName") String productName);
    List<OrderDto> selectMyOrderList(Long memberId);
    OrderDto selectOrderDetail(Long orderId);
    List<OrderItemDto> selectOrderItemsByOrderId(Long orderId);
    void updateOrderStatusToCancel(Long orderId);
    void deleteOrderItems(Long orderId);
    void deleteOrder(Long orderId);

    // ✨ 누적 결제 금액 (화면 표시용)
    Long selectTotalSpentByMember(Long memberId);
}