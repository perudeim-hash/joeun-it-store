package org.store.joeunit.board.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.store.joeunit.board.dto.BoardDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.store.joeunit.board.service.BoardService;
import org.store.joeunit.board.service.BoardCommentService;
import org.store.joeunit.member.dto.MemberDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardCommentService boardCommentService;

    @GetMapping({"", "/"})
    public String boardList(Model model) {

        List<BoardDto> list = boardService.getList();

        model.addAttribute("list", list);

        return "board/list"; // src/main/resources/templates/board/list.html 실행
    }

    @GetMapping("/write")
    public String writeForm(HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }

        return "board/write"; // src/main/resources/templates/board/write.html 실행
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }
        boardDto.setMemberId(loginMember.getMemberId());
        boardService.register(boardDto);

        return "redirect:/board";
    }


    @GetMapping("/view")
    public String view(@RequestParam("boardId") Long boardId, Model model) {
        boardService.increaseHit(boardId);

        BoardDto board = boardService.getById(boardId);

        if (board == null) {
            return "redirect:/board";
        }


        model.addAttribute("board", board);

        // 3. (추가) 댓글 리스트 가져와서 담기
        model.addAttribute("boardCommentList", boardCommentService.getList(boardId));

        return "board/view";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long boardId, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");

        if (loginMember == null) {
            return "redirect:/member/login";
        }
        BoardDto board = boardService.getById(boardId);
        if (board == null) {
            return "redirect:/board";
        }

        if (!board.getMemberId().equals(loginMember.getMemberId())) {
            return "redirect:/board/view?boardId=" + boardId;
        }
        boardService.delete(boardId);

        return "redirect:/board";
    }
}