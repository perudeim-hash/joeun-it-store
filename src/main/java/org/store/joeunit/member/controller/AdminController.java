package org.store.joeunit.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String memberList(@RequestParam(defaultValue = "1") int page,
                             HttpSession session,
                             Model model) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        int size = 30;
        int pageBlockSize = 5;

        List<MemberDto> memberList =
                memberService.findAllPaging(page, size);

        int memberCount = memberService.getMemberCount();
        int adminCount = memberService.getAdminCount();

        int totalPage =
                (int) Math.ceil((double) memberCount / size);

        int startPage =
                ((page - 1) / pageBlockSize) * pageBlockSize + 1;

        int endPage =
                Math.min(startPage + pageBlockSize - 1, totalPage);

        int prevBlockPage =
                Math.max(startPage - pageBlockSize, 1);

        int nextBlockPage =
                Math.min(startPage + pageBlockSize, totalPage);

        model.addAttribute("memberList", memberList);
        model.addAttribute("memberCount", memberCount);
        model.addAttribute("adminCount", adminCount);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("prevBlockPage", prevBlockPage);
        model.addAttribute("nextBlockPage", nextBlockPage);

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

    // 닉네임 검색기능
    @GetMapping("/member/search")
    public String searchMember(@RequestParam String type,
                               @RequestParam String keyword,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        if (!isAdmin(session)) {
            return "redirect:/";
        }

        MemberDto memberDto = null;

        if ("nickname".equals(type)) {
            memberDto = memberService.findByNickname(keyword);
        } else if ("loginId".equals(type)) {
            memberDto = memberService.findByLoginId(keyword);
        } else if ("memberId".equals(type)) {
            memberDto = memberService.findByNo(Long.parseLong(keyword));
        }

        if (memberDto == null) {
            redirectAttributes.addFlashAttribute(
                    "searchError",
                    "검색 결과가 없습니다."
            );

            return "redirect:/admin/member/list";
        }

        return "redirect:/admin/member/view?memberId="
                + memberDto.getMemberId();
    }
}