package org.store.joeunit.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private Long boardId;
    private Long memberId;
    private Long productId;
    private String boardType;

    private String title;
    private String content;
    private int hit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String nickname;
    private String productName;


}