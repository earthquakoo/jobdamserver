package com.jobdamserver.domain.member.service;

import com.jobdamserver.core.exception.CustomException;
import com.jobdamserver.core.exception.ErrorInfo;
import com.jobdamserver.domain.member.entity.Member;
import com.jobdamserver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jobdamserver.core.exception.ErrorInfo.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void createMember(Member member) {
        memberRepository.save(member);
    }


    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }
}
