package org.store.joeunit.order.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private Long orderId;          // 주문 번호 (PK)
    private Long memberId;         // 주문한 회원 번호 (FK)
    private String orderStatus;    // 주문 상태 (주문완료, 배송중 등)
    private Long totalAmount;      // 총 결제 금액
    private Date orderDate;        // 주문일자

    // 주문서에 포함된 상품 상세 리스트를 함께 다루기 위함
    private List<OrderItemDto> orderItems;
}