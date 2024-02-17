package com.app.api.member.dto;

import com.app.domain.member.constant.Role;
import com.app.domain.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponseDto {

    @Schema(description = "회원 아이디", example = "1")
    private Long memberId;

    @Schema(description = "이메일", example = "test@gmial.com")
    private String email;

    @Schema(description = "회원 이름", example = "홍길동")
    private String memberName;
    @Schema(description = "프로필 이미지 경로")
    private String profile;
    @Schema(description = "회원의 역할")
    private Role role;

    public static MemberInfoResponseDto of(Member member) {
        return MemberInfoResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .memberName(member.getEmail())
                .profile(member.getProfile())
                .role(member.getRole())
                .build();
    }
}
