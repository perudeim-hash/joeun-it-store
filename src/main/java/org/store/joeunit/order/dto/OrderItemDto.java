package org.store.joeunit.order.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long orderItemId;      // 주문 상세 번호 (PK)
    private Long orderId;          // 연결된 주문 번호 (FK)
    private Long productId;        // 상품 번호 (FK)
    private int quantity;          // 주문 수량
    private Long orderPrice;       // 상품 개당 가격
}