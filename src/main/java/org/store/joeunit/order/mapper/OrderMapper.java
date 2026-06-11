package org.store.joeunit.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.dto.OrderItemDto;
import java.util.List;

@Mapper
public interface OrderMapper {
    // 1. [C] 주문 마스터 생성
    void insertOrder(OrderDto orderDto);

    // 2. [C] 주문 상세(상품들) 생성
    void insertOrderItem(OrderItemDto orderItemDto);

    // 3. [C] 장바구니 비우기 (주문 완료 후)
    void deleteCartItemAfterOrder(Long memberId, Long productId);

    // 4. [R] 내 주문 목록 조회
    List<OrderDto> selectMyOrderList(Long memberId);

    // 5. [R/U] 주문 상세 조회 (단건)
    OrderDto selectOrderDetail(Long orderId);
    List<OrderItemDto> selectOrderItemsByOrderId(Long orderId);

    // 6. [D] 주문 취소 (실제 삭제가 아닌 상태값 업데이트)
    void updateOrderStatusToCancel(Long orderId);

    // 7. [D] 주문 내역 완전 삭제 (자식 먼저, 부모 나중)
    void deleteOrderItems(Long orderId);
    void deleteOrder(Long orderId);
}
