package com.example.praylight.domain.entity;

import com.example.praylight.application.dto.PrayerRoomDto;
import lombok.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE prayer_room SET deleted = true WHERE id = ?")
public class PrayerRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorId;

    private String title;

    private LocalDateTime lastActivityDate;

    @Column(nullable = false)
    private Boolean isDeleted;

    private String code;

    @Column(nullable = false)
    private Boolean isVisible;

    private Integer light;


    public static PrayerRoom from(PrayerRoomDto dto) {
        return PrayerRoom.builder()
                .id(dto.getId())
                .authorId(dto.getAuthorId())
                .title(dto.getTitle())
                .lastActivityDate(dto.getLastActivityDate())
                .isDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false)
                .code(dto.getCode())
                .isVisible(dto.getIsVisible() != null ? dto.getIsVisible() : false)
                .light(dto.getLight())
                .build();
    }
}
