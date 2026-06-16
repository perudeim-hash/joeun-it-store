package org.store.joeunit.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.store.joeunit.board.dto.ReplyDto;
import org.store.joeunit.board.service.ReplyService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/write")
    public String writePro(ReplyDto replyDto) {
        // 1. 댓글 저장
        replyService.register(replyDto);

        // 2. 글 번호(boardNo)를 확인해서 상세 페이지로 다시 보내줌
        // boardNo가 null이면 그냥 목록으로 보냄
        if (replyDto.getBoardNo() != null) {
            return "redirect:/board/view?boardNo=" + replyDto.getBoardNo();
        } else {
            return "redirect:/board";
        }
    }
}