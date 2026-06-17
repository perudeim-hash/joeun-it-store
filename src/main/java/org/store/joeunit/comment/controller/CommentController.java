package org.store.joeunit.comment.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.store.joeunit.comment.dto.CommentDto;
import org.store.joeunit.comment.service.CommentService;
import org.store.joeunit.member.dto.MemberDto;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/write")
    public String writeComment(CommentDto commentDto,
                               @RequestParam(value="isSecretCheck", required=false) String isSecret,
                               HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("loggedMember");
        if (loginMember != null) {
            commentDto.setMemberId(loginMember.getMemberId());

            // 대댓글일 경우 부모 댓글의 비밀 상태를 강제로 따라감
            if (commentDto.getParentId() != null) {
                CommentDto parent = commentService.getCommentById(commentDto.getParentId());
                if (parent != null) {
                    commentDto.setIsSecret(parent.getIsSecret());
                }
            } else {
                commentDto.setIsSecret(isSecret != null ? "Y" : "N");
            }
            commentService.insertComment(commentDto);
        }
        return "redirect:/board/view?id=" + commentDto.getBoardId();
    }

    @GetMapping("/delete")
    public String deleteComment(@RequestParam("id") Long commentId, @RequestParam("boardId") Long boardId) {
        commentService.deleteComment(commentId);
        return "redirect:/board/view?id=" + boardId;
    }
}