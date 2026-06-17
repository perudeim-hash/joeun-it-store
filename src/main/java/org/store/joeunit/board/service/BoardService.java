package org.store.joeunit.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.joeunit.board.dto.BoardDto;
import org.store.joeunit.board.mapper.BoardMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    // 페이지네이션 총 글 수 구하기
    public int countBoardList(String boardType, String searchType, String keyword) {
        return boardMapper.countBoardList(boardType, searchType, keyword);
    }

    // 목록 불러오기 (페이징 포함)
    public List<BoardDto> getBoardList(String boardType, String searchType, String keyword, int offset, int endRow) {
        return boardMapper.selectBoardList(boardType, searchType, keyword, offset, endRow);
    }

    public void insertBoard(BoardDto boardDto) {
        boardMapper.insertBoard(boardDto);
    }

    // ✨ 상세 불러올 때 조회수 +1 업데이트 ✨
    public BoardDto getBoardDetail(Long boardId) {
        boardMapper.updateHit(boardId);
        return boardMapper.selectBoardById(boardId);
    }

    public void updateBoard(BoardDto boardDto) {
        boardMapper.updateBoard(boardDto);
    }

    public void deleteBoard(Long boardId) {
        boardMapper.deleteBoard(boardId);
    }
}