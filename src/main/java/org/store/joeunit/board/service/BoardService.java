package org.store.joeunit.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.store.joeunit.board.dao.BoardDao;
import org.store.joeunit.board.dto.BoardDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardDao boardDao;

    @Transactional(readOnly = true)
    public List<BoardDto> getList(){
        return boardDao.getList();
    }

    @Transactional(readOnly = true)
    public BoardDto getById(Long boardId){
        return boardDao.getById(boardId);
    }

    public int register(BoardDto boardDto){
        if (boardDto.getBoardType() == null || boardDto.getBoardType().isBlank()) {
            boardDto.setBoardType("QNA");
        }
        return boardDao.insert(boardDto);
    }

    public int update(BoardDto boardDto) {
        return boardDao.update(boardDto);
    }
    public int delete(Long boardId) {
        return boardDao.delete(boardId);
    }
    public int increaseHit(Long boardId) {
        return boardDao.updateHit(boardId);
    }





}

