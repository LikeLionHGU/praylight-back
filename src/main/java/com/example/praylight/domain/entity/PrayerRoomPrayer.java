package com.example.praylight.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @JoinColumn(name="prayer_id", nullable=false)
    private Prayer prayer;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="prayer_room_id", nullable=false)
    private PrayerRoom prayerRoom;

    // getter, setter 등 필요한 메소드 추가
}
