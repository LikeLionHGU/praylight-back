package com.example.praylight.application.service;

import com.example.praylight.application.dto.MemberDto;
import com.example.praylight.domain.entity.Member;
import com.example.praylight.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public MemberDto findMemberByUid(String uid) {
        Member member = memberRepository.findByUid(uid);
        if (member == null) {
            return null;
        }
        return MemberDto.from(member);
    }


    @Transactional
    public Long addMember(MemberDto dto) {
        Member newMember = memberRepository.save(Member.from(dto));
        return newMember.getId();
    }

    @Transactional(readOnly = true)
    public MemberDto findMemberById(String userId) {
        Member member = memberRepository.findById(userId);
        if (member == null) {
            throw new RuntimeException("Member not found with ID: " + userId);
        }
        return MemberDto.from(member);
    }
}
