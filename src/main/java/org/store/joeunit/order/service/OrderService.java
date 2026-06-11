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

    @Transactional
    public void placeOrder(OrderDto orderDto) {
        // 1. orders 테이블에 주문 헤더 정보 인서트
        orderMapper.insertOrder(orderDto); // xml의 selectKey를 통해 orderDto에 방금 생성된 orderId가 자동으로 바인딩됩니다.

        // 2. 주문 안에 들어있는 상품 목록들을 하나씩 꺼내어 상세 테이블에 인서트
        if (orderDto.getOrderItems() != null) {
            for (OrderItemDto item : orderDto.getOrderItems()) {
                item.setOrderId(orderDto.getOrderId()); // 상위 주문 번호 세팅
                orderMapper.insertOrderItem(item);

                // TODO: (선택사항) 상품 파트 매퍼를 가져와서 재고 차감 로직 추가 가능
                // productMapper.decreaseStock(item.getProductId(), item.getQuantity());
            }
        }

        // TODO: (선택사항) 장바구니 파트 매퍼를 가져와서 구매한 상품들 장바구니에서 비우기
        // cartMapper.deleteOrderedItems(orderDto.getMemberId(), orderDto.getOrderItems());
    }

    public List<OrderDto> getMyOrders(Long memberId) {
        return orderMapper.selectMyOrders(memberId);
    }
}