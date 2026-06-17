package org.store.joeunit.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.joeunit.board.dao.BoardDao;
import org.store.joeunit.board.dto.BoardDto;
import java.util.List;

@Service // 이 어노테이션이 있어야 Controller가 이 서비스를 찾아올 수 있습니다.
@RequiredArgsConstructor // 생성자 주입을 자동으로 처리해줍니다.
public class BoardServiceImpl implements BoardService {

    private final BoardDao boardDao;

    @Override
    public List<BoardDto> list() { return boardDao.getList(); }

    @Override
    public void register(BoardDto boardDto) { boardDao.insert(boardDto); }

    @Override
    public void increaseViewCount(Long boardNo) { boardDao.updateHit(boardNo); }

    @Override
    public BoardDto getById(Long boardNo) { return boardDao.getById(boardNo); }
}