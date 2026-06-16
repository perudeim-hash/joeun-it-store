package org.store.joeunit.board.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BoardDto {
    private Long boardNo;
    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}