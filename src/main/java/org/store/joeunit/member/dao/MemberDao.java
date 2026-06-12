package org.store.joeunit.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.store.joeunit.member.dto.MemberDto;

import java.util.List;

@Mapper
public interface MemberDao {

    // 회원가입
    int signup(MemberDto memberDto);

    // 로그인
    MemberDto login(MemberDto memberDto);

    // 회원번호로 회원조회
    MemberDto findByNo(Long memberId);

    // 아이디 중복확인
    int idCheck(String loginId);

    // 이메일 중복확인
    int emailCheck(String email);

    // 닉네임 중복확인
    int nicknameCheck(String nickname);

    // 회원정보 수정
    int updateMember(MemberDto memberDto);

    // 회원탈퇴
    int deleteMember(Long memberId);

    // 비밀번호 변경
    int changePassword(MemberDto memberDto);

    // 전체 회원 목록
    List<MemberDto> findAll();

    // 회원 권한/등급 수정
    int updateRoleAndMembership(MemberDto memberDto);

    // 전체 회원 수
    int getMemberCount();

    // 관리자 수
    int getAdminCount();
}