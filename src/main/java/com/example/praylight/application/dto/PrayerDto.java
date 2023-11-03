package com.example.praylight.application.dto;
import lombok.*;
import com.example.praylight.domain.entity.Prayer;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrayerDto {
    private Long id;
    private Long authorId;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime expiryDate;
    private Boolean isAnonymous;
    private Boolean isDeleted;
    private Boolean isVisible;


    public static PrayerDto from(Prayer prayer) {
        return PrayerDto.builder()
                .id(prayer.getId())
                .authorId(prayer.getAuthorId())
                .content(prayer.getContent())
                .startDate(prayer.getStartDate())
                .expiryDate(prayer.getExpiryDate())
                .isAnonymous(prayer.getIsAnonymous())
                .isDeleted(prayer.getIsDeleted())
                .isVisible(prayer.getIsVisible())
                .build();
    }
}
