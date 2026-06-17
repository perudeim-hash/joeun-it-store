package org.store.joeunit.board.service;

import org.store.joeunit.board.dto.ReplyDto;
import java.util.List;

public interface ReplyService {
    List<ReplyDto> getList(Long boardNo);
    void register(ReplyDto replyDto);
}