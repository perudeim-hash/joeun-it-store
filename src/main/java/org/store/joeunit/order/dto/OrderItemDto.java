package org.store.joeunit.order.dto;

import lombok.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long orderItemId;       // PK (seq_order_item)
    private Long orderId;           // FK (orders)
    private Long productId;         // FK (product)
    private String productName;     // 상품명 (역정규화)
    private Long orderPrice;        // 상품 1개 단가
    private int quantity;           // 수량
    private Long itemTotalPrice;    // 항목 총 가격 (단가 * 수량)
    private Date createdAt;
}