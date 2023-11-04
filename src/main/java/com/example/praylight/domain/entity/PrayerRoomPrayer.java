package com.example.praylight.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrayerRoomPrayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="prayer_id", nullable=false)
    private Prayer prayer;

    @ManyToOne
    @JoinColumn(name="prayer_room_id", nullable=false)
    private PrayerRoom prayerRoom;

    // getter, setter 등 필요한 메소드 추가
}
