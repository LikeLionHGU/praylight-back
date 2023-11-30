package com.example.praylight.application.dto;

import lombok.*;
import com.example.praylight.domain.entity.Member;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

    private Long id;
    private String uid; // Google 계정의 고유한 식별자
    private String name;
    private String email;

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .uid(member.getUid())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}

