package com.app.global.resolver.memberInfo;

import com.app.domain.member.constant.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoDto {
    @Schema
    private Long memberId;
    private Role role;
}
