package org.store.joeunit.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.joeunit.member.dao.MemberDao;
import org.store.joeunit.member.dto.MemberDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;

    // 회원가입
    public int signup(MemberDto memberDto) {
        return memberDao.signup(memberDto);
    }

    // 로그인
    public MemberDto login(MemberDto memberDto) {
        return memberDao.login(memberDto);
    }

    // 회원번호로 회원조회
    public MemberDto findByNo(Long memberId) {
        return memberDao.findByNo(memberId);
    }

    // 아이디 중복확인
    public int idCheck(String loginId) {
        return memberDao.idCheck(loginId);
    }

    // 이메일 중복확인
    public int emailCheck(String email) {
        return memberDao.emailCheck(email);
    }

    // 닉네임 중복확인
    public int nicknameCheck(String nickname) {
        return memberDao.nicknameCheck(nickname);
    }

    // 회원정보 수정
    public int updateMember(MemberDto memberDto) {
        return memberDao.updateMember(memberDto);
    }

    // 회원탈퇴
    public int deleteMember(Long memberId) {
        return memberDao.deleteMember(memberId);
    }
    // 비밀번호 변경
    public int changePassword(MemberDto memberDto) {
        return memberDao.changePassword(memberDto);
    }
    // 전체 회원 목록 조회
    public List<MemberDto> findAll() {
        return memberDao.findAll();
    }
    // 회원 권한(role) 및 회원등급(membership) 수정
    public int updateRoleAndMembership(MemberDto memberDto) {
        return memberDao.updateRoleAndMembership(memberDto);
    }
    // 전체 회원 수
    public int getMemberCount() {
        return memberDao.getMemberCount();
    }

    // 관리자 수
    public int getAdminCount() {
        return memberDao.getAdminCount();
    }
    // 회원 목록 페이징 조회
    public List<MemberDto> findAllPaging(int page, int size) {

        int start = (page - 1) * size;

        return memberDao.findAllPaging(start, size);
    }
    // 닉네임으로 회원 조회
    public MemberDto findByNickname(String nickname) {
        return memberDao.findByNickname(nickname);

    }
    //아이디로 회원 조회
    public MemberDto findByLoginId(String loginId) {
        return memberDao.findByLoginId(loginId);
    }

}