package org.store.joeunit.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.store.joeunit.order.dto.OrderDto;
import org.store.joeunit.order.dto.OrderItemDto;
import java.util.List;

@Mapper
public interface OrderMapper {
    // 1. 주문 마스터 테이블(orders)에 한 줄 삽입
    void insertOrder(OrderDto orderDto);

    // 2. 주문 상세 테이블(order_item)에 한 줄 삽입
    void insertOrderItem(OrderItemDto orderItemDto);

    // 3. 내 주문 내역 전체 가져오기
    List<OrderDto> selectMyOrders(Long memberId);
}