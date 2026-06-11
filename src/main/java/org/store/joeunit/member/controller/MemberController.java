package org.store.joeunit.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.store.joeunit.member.dto.MemberDto;
import org.store.joeunit.member.service.MemberService;

import java.util.HashMap;
import java.util.Map;

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

    // 회원가입 처리 + 자동 로그인 + 메인 이동
    @PostMapping("/signup")
    public String signupProcess(MemberDto memberDto,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        int result = memberService.signup(memberDto);

        if (result > 0) {
            MemberDto loggedMember = memberService.login(memberDto);

            session.setAttribute("loggedMember", loggedMember);

            redirectAttributes.addFlashAttribute(
                    "welcomeMessage",
                    memberDto.getNickname() + "님 회원가입을 환영합니다."
            );

            return "redirect:/";
        }

        return "redirect:/member/signup";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    // 로그인 처리 + 메인 이동
    @PostMapping("/login")
    public String loginProcess(MemberDto memberDto,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        MemberDto loggedMember = memberService.login(memberDto);

        if (loggedMember == null) {
            redirectAttributes.addFlashAttribute(
                    "message",
                    "아이디 또는 비밀번호가 틀렸습니다."
            );

            return "redirect:/member/login";
        }

        session.setAttribute("loggedMember", loggedMember);

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

    // 아이디 중복확인
    @PostMapping("/id-check")
    @ResponseBody
    public Map<String, Boolean> idCheck(String loginId) {
        int result = memberService.idCheck(loginId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("available", result == 0);

        return map;
    }

    // 이메일 중복확인
    @PostMapping("/email-check")
    @ResponseBody
    public Map<String, Boolean> emailCheck(String email) {
        int result = memberService.emailCheck(email);

        Map<String, Boolean> map = new HashMap<>();
        map.put("available", result == 0);

        return map;
    }

    // 닉네임 중복확인
    @PostMapping("/nickname-check")
    @ResponseBody
    public Map<String, Boolean> nicknameCheck(String nickname) {
        int result = memberService.nicknameCheck(nickname);

        Map<String, Boolean> map = new HashMap<>();
        map.put("available", result == 0);

        return map;
    }
    // 마이페이지
    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        MemberDto loggedMember = (MemberDto) session.getAttribute("loggedMember");

        if (loggedMember == null) {
            return "redirect:/member/login";
        }

        MemberDto memberDto = memberService.findByNo(loggedMember.getMemberId());
        model.addAttribute("memberDto", memberDto);

        return "member/mypage";
    }

    // 회원정보 수정 페이지
    @GetMapping("/update")
    public String update(HttpSession session, Model model) {
        MemberDto loggedMember = (MemberDto) session.getAttribute("loggedMember");

        if (loggedMember == null) {
            return "redirect:/member/login";
        }

        MemberDto memberDto = memberService.findByNo(loggedMember.getMemberId());
        model.addAttribute("memberDto", memberDto);

        return "member/update";
    }

    // 회원정보 수정 처리
    @PostMapping("/update")
    public String updateProcess(MemberDto memberDto,
                                HttpSession session) {

        MemberDto loggedMember = (MemberDto) session.getAttribute("loggedMember");

        if (loggedMember == null) {
            return "redirect:/member/login";
        }

        memberDto.setMemberId(loggedMember.getMemberId());

        int result = memberService.updateMember(memberDto);

        if (result > 0) {
            MemberDto updatedMember = memberService.findByNo(loggedMember.getMemberId());
            session.setAttribute("loggedMember", updatedMember);
        }

        return "redirect:/member/mypage";
    }
    // 비밀번호 변경 페이지
    @GetMapping("/change-password")
    public String changePassword() {
        return "member/change-password";
    }

    // 비밀번호 변경 처리
    @PostMapping("/change-password")
    public String changePasswordProcess(String currentPassword,
                                        String newPassword,
                                        String newPasswordConfirm,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes) {

        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        if (loggedMember == null) {
            return "redirect:/member/login";
        }

        if (!loggedMember.getPassword().equals(currentPassword)) {
            redirectAttributes.addFlashAttribute(
                    "message",
                    "현재 비밀번호가 일치하지 않습니다."
            );

            return "redirect:/member/change-password";
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            redirectAttributes.addFlashAttribute(
                    "message",
                    "새 비밀번호가 일치하지 않습니다."
            );

            return "redirect:/member/change-password";
        }

        MemberDto memberDto = new MemberDto();
        memberDto.setMemberId(loggedMember.getMemberId());
        memberDto.setPassword(newPassword);

        int result = memberService.changePassword(memberDto);

        if (result > 0) {
            MemberDto updatedMember =
                    memberService.findByNo(loggedMember.getMemberId());

            session.setAttribute("loggedMember", updatedMember);

            redirectAttributes.addFlashAttribute(
                    "message",
                    "비밀번호가 변경되었습니다."
            );
        }

        return "redirect:/member/mypage";
    }
    @GetMapping("/delete")
    public String deletePage() {
        return "member/delete";
    }
    @PostMapping("/delete")
    public String deleteProcess(@RequestParam String password,
                                HttpSession session) {

        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        if(loggedMember == null){
            return "redirect:/member/login";
        }

        if(!loggedMember.getPassword().equals(password)){
            return "redirect:/member/delete";
        }

        memberService.deleteMember(
                loggedMember.getMemberId()
        );

        session.invalidate();

        return "redirect:/";
    }
}