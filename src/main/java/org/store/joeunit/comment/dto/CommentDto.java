package org.store.joeunit.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long commentId;
    private Long boardId;
    private Long memberId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String writerNickname;
    private String writerMembership;
    private String writerRole;
    private String isSecret;

    // ✨ 대댓글용 변수 추가 ✨
    private Long parentId;

    @Builder.Default
    private List<CommentDto> replies = new ArrayList<>(); // 자식(대댓글)들을 담을 바구니
}