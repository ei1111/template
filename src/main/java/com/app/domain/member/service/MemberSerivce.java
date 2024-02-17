package com.app.domain.member.service;

import com.app.domain.member.entity.Member;
import com.app.domain.member.repository.MemberRepository;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.error.exception.BusinessException;
import com.app.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSerivce {
    private final MemberRepository memberRepository;

    @Transactional
    public Member registerMember(Member member) {
        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
        if (optionalMember.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
        }
        return memberRepository.save(member);
    }
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }


    public Member findMemberByRefreshToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        //리프레시 토큰 시간 만료여부 확인
        LocalDateTime tokenExpirationTime = member.getTokenExpirationTime();

        //토큰 만료시간이 현재보다 더 작을경우
        if (tokenExpirationTime.isAfter(LocalDateTime.now())) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return member;
    }

    public Member findMemberByMemberId(Long memberId) {
        return memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXIST)
        );
    }
}
