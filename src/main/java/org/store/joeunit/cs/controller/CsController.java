package org.store.joeunit.cs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.store.joeunit.board.mapper.BoardMapper;
import org.store.joeunit.common.dto.SiteSettingsDto;
import org.store.joeunit.common.mapper.SiteSettingsMapper;
import jakarta.servlet.http.HttpSession;
import org.store.joeunit.member.dto.MemberDto;

@Controller
@RequiredArgsConstructor
public class CsController {

    private final BoardMapper boardMapper;
    private final SiteSettingsMapper siteSettingsMapper;

    // 1. 고객센터 메인 화면 (FAQ와 전화번호 DB에서 불러오기)
    @GetMapping("/cs/main")
    public String csMain(Model model) {
        model.addAttribute("siteSettings", siteSettingsMapper.getSettings());
        model.addAttribute("faqList", boardMapper.findTop5Faq());
        return "cs/main";
    }

    // 2. 관리자 전용 전화번호 설정 화면
    @GetMapping("/admin/settings")
    public String adminSettings(Model model, HttpSession session) {
        MemberDto loginUser = (MemberDto) session.getAttribute("loggedMember");
        if (loginUser == null || !"ADMIN".equals(loginUser.getRole())) {
            return "redirect:/"; // 관리자가 아니면 쫓아냄
        }
        model.addAttribute("siteSettings", siteSettingsMapper.getSettings());
        return "admin/settings";
    }

    // 3. 관리자 전화번호 수정 처리
    @PostMapping("/admin/settings/update")
    public String updateSettings(SiteSettingsDto dto) {
        siteSettingsMapper.updateSettings(dto);
        return "redirect:/cs/main";
    }
}