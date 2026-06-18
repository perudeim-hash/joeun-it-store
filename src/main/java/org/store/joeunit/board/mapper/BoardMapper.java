package org.store.joeunit.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.store.joeunit.board.dto.BoardDto;

import java.util.List;

@Mapper
public interface BoardMapper {

    // ✨ 전체 글 개수 구하기 (페이지네이션용) ✨
    int countBoardList(
            @Param("boardType") String boardType,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword
    );

    // ✨ 10개씩 잘라서 가져오기 (offset, endRow 추가) ✨
    List<BoardDto> selectBoardList(
            @Param("boardType") String boardType,
            @Param("searchType") String searchType,
            @Param("keyword") String keyword,
            @Param("offset") int offset,
            @Param("endRow") int endRow
    );

    void insertBoard(BoardDto boardDto);
    BoardDto selectBoardById(Long boardId);
    void updateBoard(BoardDto boardDto);
    void deleteBoard(Long boardId);

    // ✨ 조회수 증가 ✨
    void updateHit(Long boardId);

    // ✨ 고객센터 메인 화면에 띄울 FAQ 5개 가져오기 ✨
    List<BoardDto> findTop5Faq();
}