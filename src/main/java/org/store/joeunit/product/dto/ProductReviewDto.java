package org.store.joeunit.product.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReviewDto {

    private Long reviewId;
    private Integer productId;
    private Long memberId;

    private Integer rating;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String nickname;
}