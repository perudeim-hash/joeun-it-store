package org.store.joeunit.member.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberDto {

    private Long memberId;

    private String loginId;
    private String password;
    private String nickname;

    private String email;
    private String phone;

    private String zipcode;
    private String address;
    private String detailAddress;

    private String role;
    private String membership;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}