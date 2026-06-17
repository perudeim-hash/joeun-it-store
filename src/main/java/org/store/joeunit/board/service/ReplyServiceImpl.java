package org.store.joeunit.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.joeunit.board.dao.ReplyDao;
import org.store.joeunit.board.dto.ReplyDto;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyDao replyDao;

    @Override
    public List<ReplyDto> getList(Long boardNo) {
        return replyDao.getReplyList(boardNo);
    }

    @Override
    public void register(ReplyDto replyDto) {
        replyDao.insert(replyDto);
    }
}