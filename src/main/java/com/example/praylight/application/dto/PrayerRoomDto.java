package com.example.praylight.application.dto;
import com.example.praylight.domain.entity.PrayerRoom;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrayerRoomDto {
    private Long id;
    private Long author;
    private String title;
    private LocalDateTime lastActivityDate;
    private Boolean isDeleted;
    private String code;
    private Boolean isVisible;
    private Integer light;

    public static PrayerRoomDto from(Optional<PrayerRoom> optionalPrayerRoom) {
        if (optionalPrayerRoom.isPresent()) {
            PrayerRoom prayerRoom = optionalPrayerRoom.get();
            return PrayerRoomDto.builder()
                    .id(prayerRoom.getId())
                    .author(prayerRoom.getAuthor().getId())
                    .title(prayerRoom.getTitle())
                    .lastActivityDate(prayerRoom.getLastActivityDate())
                    .isDeleted(prayerRoom.getIsDeleted())
                    .code(prayerRoom.getCode())
                    .isVisible(prayerRoom.getIsVisible())
                    .light(prayerRoom.getLight())
                    .build();
        } else {
            return null;
        }
    }
}
