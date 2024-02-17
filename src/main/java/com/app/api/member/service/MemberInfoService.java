package com.app.api.member.service;

import com.app.api.member.dto.MemberInfoResponseDto;
import com.app.domain.member.entity.Member;
import com.app.domain.member.repository.MemberRepository;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService {
    private final MemberRepository memberRepository;
    public MemberInfoResponseDto getMemberInfo(Long memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXIST));
        return MemberInfoResponseDto.of(member);

    }
}
