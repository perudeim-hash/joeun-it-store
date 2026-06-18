package org.store.joeunit.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.joeunit.comment.dto.CommentDto;
import org.store.joeunit.comment.mapper.CommentMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    public void insertComment(CommentDto commentDto) { commentMapper.insertComment(commentDto); }
    public CommentDto getCommentById(Long commentId) { return commentMapper.selectCommentById(commentId); }
    public void deleteComment(Long commentId) { commentMapper.deleteComment(commentId); }

    // ✨ 댓글과 대댓글을 분리해서 조립하는 마법의 로직 ✨
    public List<CommentDto> getCommentsByBoardId(Long boardId) {
        List<CommentDto> allComments = commentMapper.selectCommentsByBoardId(boardId);
        List<CommentDto> parentComments = new ArrayList<>();

        // 1. 부모 댓글(대댓글이 아닌 일반 댓글)만 먼저 추려냄
        for (CommentDto c : allComments) {
            if (c.getParentId() == null) {
                parentComments.add(c);
            }
        }

        // 2. 대댓글들을 자신의 부모 댓글(replies 리스트)에 쏙쏙 집어넣음
        for (CommentDto c : allComments) {
            if (c.getParentId() != null) {
                for (CommentDto parent : parentComments) {
                    if (parent.getCommentId().equals(c.getParentId())) {
                        parent.getReplies().add(c);
                        break;
                    }
                }
            }
        }
        return parentComments;
    }
}