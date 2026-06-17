package org.store.joeunit.board.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.store.joeunit.board.dto.BoardCommentDto;
import org.store.joeunit.board.service.BoardCommentService;
import org.store.joeunit.member.dto.MemberDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board-comment")
public class BoardCommentController {

    private final BoardCommentService boardCommentService;


    @PostMapping("/write")
    public String writePro(BoardCommentDto boardCommentDto, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        boardCommentDto.setMemberId(loginMember.getMemberId());
        boardCommentService.register(boardCommentDto);
        return "redirect:/board/view?boardId=" + boardCommentDto.getBoardId();
    }


    @PostMapping("/delete")
    public String delete(@RequestParam Long commentId,
                         @RequestParam Long boardId,
                         HttpSession session) {

        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        boardCommentService.delete(commentId);
        return "redirect:/board/view?boardId=" + boardId;
    }
}