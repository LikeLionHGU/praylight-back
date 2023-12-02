package com.example.praylight.domain.entity;

import com.example.praylight.application.dto.PrayerRoomDto;
import com.example.praylight.application.service.MemberService;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE prayer_room SET deleted = true WHERE id = ?")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PrayerRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="author", nullable=false)
    private Member author;

    private String title;

    private LocalDateTime lastActivityDate;

    @Column(nullable = false)
    private Boolean isDeleted;

    private String code;

    @Column(nullable = false)
    private Boolean isVisible;

    private Integer light;

@OneToMany(mappedBy = "prayerRoom", cascade = CascadeType.ALL)
private List<MemberPrayerRoom> memberPrayerRooms = new ArrayList<>();

@OneToMany(mappedBy = "prayerRoom")
@JsonManagedReference
private List<PrayerRoomPrayer> prayerRoomPrayers = new ArrayList<>();


    public static PrayerRoom from(PrayerRoomDto dto, MemberService memberService) {
        Member author = memberService.getMemberById(dto.getAuthor());
        return PrayerRoom.builder()
                .id(dto.getId())
                .author(author)
                .title(dto.getTitle())
                .lastActivityDate(dto.getLastActivityDate())
                .isDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false)
                .code(dto.getCode())
                .isVisible(dto.getIsVisible() != null ? dto.getIsVisible() : false)
                .light(dto.getLight())
                .build();
    }

    public void increaseLight() {
        this.light += 1;
    }
}
