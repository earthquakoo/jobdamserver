package com.jobdamserver.domain.auth.service;

import com.jobdamserver.core.exception.CustomException;
import com.jobdamserver.core.exception.ErrorInfo;
import com.jobdamserver.core.jwt.JwtTokenProvider;
import com.jobdamserver.core.jwt.dto.JwtTokenDto;
import com.jobdamserver.domain.auth.controller.response.LoginResponse;
import com.jobdamserver.domain.auth.controller.response.SignUpResponse;
import com.jobdamserver.domain.member.entity.Member;
import com.jobdamserver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.jobdamserver.core.exception.ErrorInfo.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(String name, String rawPassword) {
        Optional<Member> optionalMember = memberRepository.findByName(name);

        if (optionalMember.isPresent()) {
            throw new CustomException(MEMBER_ALREADY_EXIST);
        }

        Member member = Member.builder()
                .name(name)
                .password(passwordEncoder.encode(rawPassword))
                .ownedRooms(0)
                .build();

        memberRepository.save(member);

        return SignUpResponse.builder()
                .name(member.getName())
                .build();
    }

    public LoginResponse login(String name, String password) {
        Optional<Member> optionalMember = memberRepository.findByName(name);

        if (optionalMember.isEmpty()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        }
        Member member = optionalMember.get();

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(INVALID_PASSWORD);
        }
        JwtTokenDto jwtTokenDto = jwtTokenProvider.generateToken(member.getId());
        return LoginResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .accessToken(jwtTokenDto.getAccessToken())
                .build();
    }
}
