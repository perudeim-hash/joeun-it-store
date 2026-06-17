package org.store.joeunit.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCommentDto {
    private Long commentId;
    private Long boardId;
    private Long memberId;
    private String content;
    // 댓글 내용
    private LocalDateTime createdAt; // 작성일
    private LocalDateTime updatedAt; // 작성일

    private String nickname;
}