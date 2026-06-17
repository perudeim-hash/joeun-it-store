package org.store.joeunit.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
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
    private Integer hit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String writerNickname;
    private String writerMembership;
    private String writerRole;

    private String imageName;
    private String imagePath;
    private String imageName2;
    private String imagePath2;
    private String imageName3;
    private String imagePath3;

    private boolean imageChanged;

    // ✨ Q&A 답변 상태 (답변대기 / 답변완료) ✨
    private String replyStatus;
}