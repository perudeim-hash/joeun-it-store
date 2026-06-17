package org.store.joeunit.board.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.store.joeunit.board.dto.BoardCommentDto;
import org.store.joeunit.board.dto.BoardDto;

import java.util.List;

@Mapper
public interface BoardDao {

    List<BoardDto> getList();

    BoardDto getById(@Param("boardId") Long boardId);

    int insert(BoardDto boardDto);
    int update(BoardDto boardDto);
    int updateHit(@Param("boardId") Long boardId);

    int delete(@Param("boardId") Long boardId);
}