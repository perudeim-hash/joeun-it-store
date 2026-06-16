package org.store.joeunit.board.dao;

import org.apache.ibatis.annotations.Mapper;
import org.store.joeunit.board.dto.ReplyDto;
import java.util.List;

@Mapper
public interface ReplyDao {
    // 특정 게시글의 댓글 목록 조회
    List<ReplyDto> getReplyList(Long boardNo);

    // 댓글 등록
    int insert(ReplyDto replyDto);
}