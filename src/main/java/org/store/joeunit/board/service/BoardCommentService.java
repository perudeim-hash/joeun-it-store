package org.store.joeunit.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.joeunit.board.dao.BoardCommentDao;
import org.store.joeunit.board.dto.BoardCommentDto;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCommentService {
    private final BoardCommentDao boardCommentDao;

    @Transactional(readOnly = true)
    public List<BoardCommentDto> getList(Long boardId) {
        return boardCommentDao.getList(boardId);
    }


    public int register(BoardCommentDto boardCommentDto) {
        return boardCommentDao.insert(boardCommentDto);
    }

    public int delete(Long commentId) {
        return boardCommentDao.delete(commentId);
    }


}