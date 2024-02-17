package com.app.api.login.service;

import com.app.domain.member.entity.Member;
import com.app.domain.member.service.MemberSerivce;
import com.app.global.jwt.dto.JwtTokenDto;
import com.app.global.jwt.service.TokenManager;
import com.app.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final MemberSerivce memberSerivce;
    private final TokenManager tokenManager;

    public ResponseEntity<ResponseDto> login(Member member) {
        Optional<Member> optionalMember = memberSerivce.findMemberByEmail(member.getEmail());
        JwtTokenDto jwtTokenDto = new JwtTokenDto();

        if (!optionalMember.isEmpty()) {
            //토큰 생성
             jwtTokenDto = tokenManager.createJwtTokenDto(member.getMemberId(), member.getRole());
        }

        return ResponseEntity.ok()
                .body(ResponseDto.of(jwtTokenDto));
    }
}
