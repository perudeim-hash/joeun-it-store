package org.store.joeunit.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.store.joeunit.member.dto.MemberDto;
import org.store.joeunit.member.service.MemberService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;

    // 관리자 권한 확인
    private boolean isAdmin(HttpSession session) {
        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        return loggedMember != null
                && "ADMIN".equals(loggedMember.getRole());
    }

    // 회원 목록
    @GetMapping("/member/list")
    public String memberList(HttpSession session,
                             Model model) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        List<MemberDto> memberList = memberService.findAll();

        int memberCount = memberService.getMemberCount();

        int adminCount = memberService.getAdminCount();

        model.addAttribute("memberList", memberList);
        model.addAttribute("memberCount", memberCount);
        model.addAttribute("adminCount", adminCount);

        return "member/member-list";
    }

    // 회원 상세보기
    @GetMapping("/member/view")
    public String memberView(@RequestParam Long memberId,
                             HttpSession session,
                             Model model) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        MemberDto memberDto = memberService.findByNo(memberId);
        model.addAttribute("memberDto", memberDto);

        return "member/member-view";
    }

    // 회원 권한 및 등급 수정
    @PostMapping("/member/update-role")
    public String updateRole(MemberDto memberDto,
                             HttpSession session) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        if ("ADMIN".equals(memberDto.getRole())) {
            memberDto.setMembership("ADMIN");
        }

        if ("USER".equals(memberDto.getRole())
                && "ADMIN".equals(memberDto.getMembership())) {
            memberDto.setMembership("BRONZE");
        }

        memberService.updateRoleAndMembership(memberDto);

        return "redirect:/admin/member/list";
    }
}