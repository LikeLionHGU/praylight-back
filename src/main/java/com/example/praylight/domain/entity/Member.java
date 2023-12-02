package com.example.praylight.domain.entity;

import com.example.praylight.application.dto.MemberDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEMBER")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "uid")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Google 계정의 고유한 식별자
    @Column(unique = true)
    private String uid;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String email;

    @OneToMany(mappedBy = "author")
    private List<Prayer> prayers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    @JsonManagedReference
    private List<PrayTogether> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberPrayerRoom> memberPrayerRooms = new ArrayList<>();


    public static Member from(MemberDto dto) {
        return Member.builder()
                .id(dto.getId())
                .uid(dto.getUid())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }

}

