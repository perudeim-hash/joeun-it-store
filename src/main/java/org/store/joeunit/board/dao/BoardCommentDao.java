package org.store.joeunit.board.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.store.joeunit.board.dto.BoardCommentDto;
import org.store.joeunit.board.dto.BoardDto;

import java.util.List;

@Mapper
public interface BoardCommentDao {
    List<BoardCommentDto> getList(@Param("boardId") Long boardId);

    int insert(BoardCommentDto boardCommentDto);

    int delete(@Param("commentId") Long commentId);
}