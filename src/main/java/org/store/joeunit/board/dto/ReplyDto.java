package org.store.joeunit.board.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReplyDto {
    private Long replyNo;      // 댓글 번호 (PK)
    private Long boardNo;      // 게시글 번호 (FK)
    private String writer;     // 댓글 작성자
    private String content;    // 댓글 내용
    private LocalDateTime createdAt; // 작성일
}