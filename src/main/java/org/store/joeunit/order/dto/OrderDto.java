package org.store.joeunit.order.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private Long orderId;           // PK (seq_orders)
    private Long memberId;          // FK (shop_member)
    private Long totalPrice;        // 총 가격
    private Long discountPrice;     // 할인 가격
    private Long finalPrice;        // 최종 결제 가격
    private String memberMembership;// 회원 등급
    private String orderStatus;     // ORDER_COMPLETE, DELIVERING, ORDER_CANCEL 등
    private String receiverName;    // 수령인
    private String receiverPhone;   // 수령인 전화번호
    private String zipcode;         // 우편번호
    private String address;         // 주소
    private String detailAddress;   // 상세주소
    private Date createdAt;         // 주문일시
    private Date updatedAt;         // 수정일시

    // 주문 상세 목록 (1:N)
    private List<OrderItemDto> orderItems;
}