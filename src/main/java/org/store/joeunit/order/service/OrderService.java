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

    // 1. [C] 장바구니 상품 결제 (주문 생성)
    @Transactional
    public void placeOrderFromCart(OrderDto orderDto) {
        // 1) 주문 헤더(orders) 저장 (xml에서 selectKey로 PK 자동 생성됨)
        orderDto.setOrderStatus("ORDER_COMPLETE");
        orderMapper.insertOrder(orderDto);

        // 2) 주문 상세(order_item) 리스트 저장 및 장바구니 비우기
        if (orderDto.getOrderItems() != null) {
            for (OrderItemDto item : orderDto.getOrderItems()) {
                item.setOrderId(orderDto.getOrderId()); // 생성된 상위 주문번호 세팅
                orderMapper.insertOrderItem(item);      // 상세 저장

                // 장바구니에서 해당 상품 삭제
                orderMapper.deleteCartItemAfterOrder(orderDto.getMemberId(), item.getProductId());

                // TODO: 나중에 상품(Product) 파트원과 연결해서 재고(stock) 감소 로직 추가
            }
        }
    }

    // 2. [R] 내 주문 목록 조회
    public List<OrderDto> getOrderList(Long memberId) {
        return orderMapper.selectMyOrderList(memberId);
    }

    // 3. [U] 주문 상세 조회
    public OrderDto getOrderDetail(Long orderId) {
        // 주문 마스터 정보 가져오기
        OrderDto order = orderMapper.selectOrderDetail(orderId);
        // 주문 안에 포함된 상품 리스트 가져오기
        List<OrderItemDto> items = orderMapper.selectOrderItemsByOrderId(orderId);
        order.setOrderItems(items); // 합체
        return order;
    }

    // 4. [D] 주문 취소 (상태 변경)
    @Transactional
    public void cancelOrder(Long orderId) {
        orderMapper.updateOrderStatusToCancel(orderId);
        // TODO: 나중에 취소 시 상품 재고 다시 복구시키는 로직 추가
    }
    // 5. [D] 주문 내역 완전 삭제 (DB에서 완전히 날리기)
    @Transactional
    public void deleteOrderHistory(Long orderId) {
        orderMapper.deleteOrderItems(orderId); // 1. 자식(상세) 먼저 삭제
        orderMapper.deleteOrder(orderId);      // 2. 부모(마스터) 최종 삭제
    }
}