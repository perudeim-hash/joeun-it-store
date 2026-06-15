package org.store.joeunit.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.store.joeunit.member.dto.MemberDto;
import org.store.joeunit.member.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signupProcess(MemberDto memberDto,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        memberService.signup(memberDto);

        // 가입 후 자동 로그인
        MemberDto loggedMember =
                memberService.login(memberDto);

        session.setAttribute("loggedMember", loggedMember);

        redirectAttributes.addFlashAttribute(
                "welcomeMessage",
                loggedMember.getNickname() + "님 가입을 환영합니다."
        );

        return "redirect:/";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String loginProcess(MemberDto memberDto,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        MemberDto loggedMember =
                memberService.login(memberDto);

        if (loggedMember == null) {

            redirectAttributes.addFlashAttribute(
                    "loginError",
                    "아이디 또는 비밀번호가 틀렸습니다."
            );

            return "redirect:/member/login";
        }

        session.setAttribute(
                "loggedMember",
                loggedMember
        );

        redirectAttributes.addFlashAttribute(
                "welcomeMessage",
                loggedMember.getNickname() + "님 환영합니다."
        );

        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }

    // 마이페이지
    @GetMapping("/mypage")
    public String mypage(HttpSession session,
                         Model model) {

        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        if (loggedMember == null) {
            return "redirect:/member/login";
        }

        MemberDto memberDto =
                memberService.findByNo(
                        loggedMember.getMemberId()
                );

        model.addAttribute(
                "memberDto",
                memberDto
        );

        return "member/mypage";
    }

    // 회원정보 수정 페이지
    @GetMapping("/update")
    public String update(HttpSession session,
                         Model model) {

        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        if (loggedMember == null) {
            return "redirect:/member/login";
        }

        MemberDto memberDto =
                memberService.findByNo(
                        loggedMember.getMemberId()
                );

        model.addAttribute(
                "memberDto",
                memberDto
        );

        return "member/update";
    }

    // 회원정보 수정 처리
    @PostMapping("/update")
    public String updateProcess(MemberDto memberDto,
                                HttpSession session) {

        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        memberDto.setMemberId(
                loggedMember.getMemberId()
        );

        memberService.updateMember(memberDto);

        return "redirect:/member/mypage";
    }

    // 비밀번호 변경 페이지
    @GetMapping("/change-password")
    public String changePassword() {
        return "member/change-password";
    }

    // 비밀번호 변경 처리
    @PostMapping("/change-password")
    public String changePasswordProcess(
            @RequestParam String password,
            HttpSession session) {

        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        MemberDto memberDto = new MemberDto();

        memberDto.setMemberId(
                loggedMember.getMemberId()
        );

        memberDto.setPassword(password);

        memberService.changePassword(memberDto);

        return "redirect:/member/mypage";
    }

    // 회원탈퇴 페이지
    @GetMapping("/delete")
    public String deletePage() {
        return "member/delete";
    }

    // 회원탈퇴 처리
    @PostMapping("/delete")
    public String deleteProcess(HttpSession session) {

        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        memberService.deleteMember(
                loggedMember.getMemberId()
        );

        session.invalidate();

        return "redirect:/";
    }
}