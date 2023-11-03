package com.example.praylight.application.dto;
import com.example.praylight.domain.entity.PrayerRoom;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrayerRoomDto {
    private Long id;
    private Long authorId;
    private String title;
    private LocalDateTime lastActivityDate;
    private Boolean isDeleted;
    private String code;
    private Boolean isVisible;
    private Integer light;

    public static PrayerRoomDto from(PrayerRoom prayerRoom) {
        return PrayerRoomDto.builder()
                .id(prayerRoom.getId())
                .authorId(prayerRoom.getAuthorId())
                .title(prayerRoom.getTitle())
                .lastActivityDate(prayerRoom.getLastActivityDate())
                .isDeleted(prayerRoom.getIsDeleted())
                .code(prayerRoom.getCode())
                .isVisible(prayerRoom.getIsVisible())
                .light(prayerRoom.getLight())
                .build();
    }
}
