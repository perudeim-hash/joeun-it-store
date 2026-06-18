package org.store.joeunit.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.store.joeunit.comment.dto.CommentDto;
import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(CommentDto commentDto);
    List<CommentDto> selectCommentsByBoardId(Long boardId);
    CommentDto selectCommentById(Long commentId); // 부모 댓글 속성 확인용
    void deleteComment(@Param("commentId") Long commentId);
}