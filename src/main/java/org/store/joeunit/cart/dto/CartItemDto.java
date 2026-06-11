package org.store.joeunit.cart.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("CartItemDto")
public class CartItemDto {

    private int cartItemId;
    private int memberId;
    private int productId;
    private int quantity;
    private int itemTotalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
