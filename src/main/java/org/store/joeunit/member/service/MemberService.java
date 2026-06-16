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

    // 아이디로 회원 조회
    public MemberDto findByLoginId(String loginId) {
        return memberDao.findByLoginId(loginId);
    }

    // 아이디 찾기
    public MemberDto findByEmailAndPhone(String email, String phone) {
        return memberDao.findByEmailAndPhone(email, phone);
    }

    // 비밀번호 찾기
    public MemberDto findForPasswordReset(String loginId,
                                          String email,
                                          String phone) {
        return memberDao.findForPasswordReset(loginId, email, phone);
    }

    // 닉네임 + 이메일로 아이디 찾기
    public MemberDto findByNicknameAndEmail(String nickname,
                                            String email) {
        return memberDao.findByNickNameAndEmail(nickname, email);
    }

    // 아이디 + 이메일로 비밀번호 재설정 대상 찾기
    public MemberDto findByLoginIdAndEmail(String loginId,
                                           String email) {
        return memberDao.findByLoginIdAndEmail(loginId, email);
    }

    // BRONZE 회원 수
    public int getBronzeCount() {
        return memberDao.getBronzeCount();
    }

    // SILVER 회원 수
    public int getSilverCount() {
        return memberDao.getSilverCount();
    }

    // GOLD 회원 수
    public int getGoldCount() {
        return memberDao.getGoldCount();
    }

    // VIP 회원 수
    public int getVipCount() {
        return memberDao.getVipCount();
    }

    // 누적 구매금액 기준 회원등급 계산
    public String calculateMembership(Long totalPurchase) {

        if (totalPurchase == null) {
            return "BRONZE";
        }

        if (totalPurchase >= 10_000_000) {
            return "VIP";
        }

        if (totalPurchase >= 5_000_000) {
            return "GOLD";
        }

        if (totalPurchase >= 3_000_000) {
            return "SILVER";
        }

        return "BRONZE";
    }

    // 등급별 할인율 계산
    public int calculateDiscountRate(String membership) {

        if ("VIP".equals(membership)) {
            return 10;
        }

        if ("GOLD".equals(membership)) {
            return 5;
        }

        if ("SILVER".equals(membership)) {
            return 3;
        }

        return 1;
    }

    // 주문 완료 시 누적 구매금액 증가 + 회원등급 자동 갱신
    public void updateMembership(Long memberId,
                                 Long finalPrice) {

        MemberDto member =
                memberDao.findByNo(memberId);

        Long currentTotal =
                member.getTotalPurchase() != null
                        ? member.getTotalPurchase()
                        : 0L;

        Long orderPrice =
                finalPrice != null
                        ? finalPrice
                        : 0L;

        Long totalPurchase =
                currentTotal + orderPrice;

        String membership =
                calculateMembership(totalPurchase);

        member.setTotalPurchase(totalPurchase);
        member.setMembership(membership);

        memberDao.updateTotalPurchase(member);
    }
}