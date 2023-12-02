package com.example.praylight.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_prayer_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class MemberPrayerRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "prayer_room_id")
    private PrayerRoom prayerRoom;

    @Column(name = "last_clicked")
    private LocalDateTime lastClicked = LocalDateTime.of(0, 1, 1, 0, 0);


    public static MemberPrayerRoom create(Member member, PrayerRoom prayerRoom) {
        MemberPrayerRoom memberPrayerRoom = new MemberPrayerRoom();
        memberPrayerRoom.setMember(member);
        memberPrayerRoom.setPrayerRoom(prayerRoom);
        return memberPrayerRoom;
    }
}
