package org.store.joeunit.board.service;

import org.store.joeunit.board.dto.BoardDto;
import java.util.List;

public interface BoardService {
    // 1. 목록 조회
    List<BoardDto> list();

    // 2. 글 등록
    void register(BoardDto boardDto);

    // 3. 조회수 증가 (상세 보기용)
    void increaseViewCount(Long boardNo);

    // 4. 상세 조회
    BoardDto getById(Long boardNo);
}