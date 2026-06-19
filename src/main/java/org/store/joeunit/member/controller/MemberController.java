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

    // 회원가입 처리
    @PostMapping("/signup")
    public String signupProcess(MemberDto memberDto,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        // 아이디 형식 검사
        if (!memberDto.getLoginId().matches("^[a-z0-9_]{4,20}$")) {
            redirectAttributes.addFlashAttribute(
                    "signupError",
                    "아이디는 영문 소문자, 숫자, _ 만 4~20자로 입력하세요."
            );

            return "redirect:/member/signup";
        }
        // 아이디 중복 검사
        if (memberService.idCheck(memberDto.getLoginId()) > 0) {
            redirectAttributes.addFlashAttribute(
                    "signupError",
                    "이미 사용중인 아이디입니다."
            );

            return "redirect:/member/signup";
        }

// 이메일 중복 검사
        if (memberDto.getEmail() != null
                && !memberDto.getEmail().trim().isEmpty()
                && memberService.emailCheck(memberDto.getEmail()) > 0) {

            redirectAttributes.addFlashAttribute(
                    "signupError",
                    "이미 사용중인 이메일입니다."
            );

            return "redirect:/member/signup";
        }

// 닉네임 중복 검사
        if (memberService.nicknameCheck(memberDto.getNickname()) > 0) {
            redirectAttributes.addFlashAttribute(
                    "signupError",
                    "이미 사용중인 닉네임입니다."
            );

            return "redirect:/member/signup";
        }

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

    // 아이디 중복확인
    @PostMapping("/id-check")
    @ResponseBody
    public Map<String, Object> idCheck(@RequestParam String loginId) {

        int count = memberService.idCheck(loginId);

        Map<String, Object> result = new HashMap<>();

        // count가 0이면 사용 가능
        result.put("available", count == 0);

        return result;
    }

    // 이메일 중복확인
    @PostMapping("/email-check")
    @ResponseBody
    public Map<String, Object> emailCheck(@RequestParam String email) {

        int count = memberService.emailCheck(email);

        Map<String, Object> result = new HashMap<>();

        result.put("available", count == 0);

        return result;
    }

    // 닉네임 중복확인
    @PostMapping("/nickname-check")
    @ResponseBody
    public Map<String, Object> nicknameCheck(@RequestParam String nickname) {

        int count = memberService.nicknameCheck(nickname);

        Map<String, Object> result = new HashMap<>();

        result.put("available", count == 0);

        return result;
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

        model.addAttribute("memberDto", memberDto);

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

        model.addAttribute("memberDto", memberDto);

        return "member/update";
    }

    // 회원정보 수정 처리
    @PostMapping("/update")
    public String updateProcess(MemberDto memberDto,
                                HttpSession session) {

        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        if (loggedMember == null) {
            return "redirect:/member/login";
        }

        memberDto.setMemberId(
                loggedMember.getMemberId()
        );

        memberService.updateMember(memberDto);

        MemberDto updatedMember =
                memberService.findByNo(loggedMember.getMemberId());

        session.setAttribute("loggedMember", updatedMember);

        return "redirect:/member/mypage";
    }

    // 비밀번호 변경 페이지
    @GetMapping("/change-password")
    public String changePassword() {
        return "member/change-password";
    }

    // 비밀번호 변경 처리
    @PostMapping("/change-password")
    public String changePasswordProcess(@RequestParam String currentPassword,
                                        @RequestParam String newPassword,
                                        @RequestParam String newPasswordConfirm,
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

        if (currentPassword.equals(newPassword)) {
            redirectAttributes.addFlashAttribute(
                    "message",
                    "새 비밀번호는 현재 비밀번호와 다르게 입력해야 합니다."
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

        memberDto.setMemberId(
                loggedMember.getMemberId()
        );

        memberDto.setPassword(newPassword);

        memberService.changePassword(memberDto);

        MemberDto updatedMember =
                memberService.findByNo(loggedMember.getMemberId());

        session.setAttribute("loggedMember", updatedMember);

        redirectAttributes.addFlashAttribute(
                "message",
                "비밀번호가 변경되었습니다."
        );

        return "redirect:/";
    }

    // 회원탈퇴 페이지
    @GetMapping("/delete")
    public String deletePage(HttpSession session) {

        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        if (loggedMember == null) {
            return "redirect:/member/login";
        }

        if ("ADMIN".equals(loggedMember.getRole())) {
            return "redirect:/member/mypage";
        }

        return "member/delete";
    }

    // 회원탈퇴 처리
    @PostMapping("/delete")
    public String deleteProcess(@RequestParam String password,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        MemberDto loggedMember =
                (MemberDto) session.getAttribute("loggedMember");

        if (loggedMember == null) {
            return "redirect:/member/login";
        }

        if ("ADMIN".equals(loggedMember.getRole())) {
            return "redirect:/member/mypage";
        }

        if (!loggedMember.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute(
                    "message",
                    "비밀번호가 일치하지 않습니다."
            );

            return "redirect:/member/delete";
        }

        memberService.deleteMember(
                loggedMember.getMemberId()
        );

        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/find-id")
    public String findId(HttpSession session) {

        MemberDto loginMember =
                (MemberDto) session.getAttribute("loggedMember");

        if(loginMember != null){
            return "redirect:/";
        }

        return "member/find-id";
    }

    @PostMapping("/find-id")
    public String findId(
            @RequestParam String nickname,
            @RequestParam String email,
            Model model
    ) {

        MemberDto memberDto =
                memberService.findByNicknameAndEmail(
                        nickname,
                        email
                );

        if (memberDto == null) {

            model.addAttribute(
                    "findError",
                    "이메일 또는 닉네임이 잘못되었습니다."
            );

            return "member/find-id";
        }

        model.addAttribute(
                "loginId",
                memberDto.getLoginId()
        );

        return "member/find-id-result";
    }

    @GetMapping("/find-password")
    public String findPassword(HttpSession session) {

        MemberDto loginMember =
                (MemberDto) session.getAttribute("loggedMember");

        if(loginMember != null){
            return "redirect:/";
        }

        return "member/find-password";
    }
    @PostMapping("/find-password")
    public String findPassword(
            @RequestParam String loginId,
            @RequestParam String email,
            Model model
    ) {
        MemberDto memberDto =
                memberService.findByLoginIdAndEmail(
                        loginId,
                        email
                );
        if(memberDto==null) {

            model.addAttribute(
                    "findError",
                    "아이디 또는 이메일이 잘못되었습니다."
            );

            return "member/find-password";
        }

        model.addAttribute(
                "memberId",
                memberDto.getMemberId()
        );

        return "member/reset-password";
    }
    @PostMapping("/reset-password")
    public  String resetPassword(
            Long memberId,
            String password
    ) {

        MemberDto memberDto = new MemberDto();

        memberDto.setMemberId(memberId);
        memberDto.setPassword(password);

        memberService.changePassword(memberDto);

        return "redirect:/member/login";
    }
}