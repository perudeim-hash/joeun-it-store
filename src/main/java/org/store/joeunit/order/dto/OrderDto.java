package org.store.joeunit.order.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long orderId;
    private Long memberId;

    // 금액 관련 필드
    private Long totalPrice;
    private Long finalPrice;
    private Integer discountRate;
    private Long discountAmount;
    private Long discountPrice; // Mapper에서 사용되는 이름

    // 주문 정보
    private String orderStatus;
    private String orderDate;
    private String memberMembership; // 등급 할인용 필드

    // 배송 정보
    private String receiverName;
    private String receiverPhone;
    private String zipcode;
    private String address;
    private String detailAddress;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 주문 상품 리스트
    private List<OrderItemDto> orderItems;
}