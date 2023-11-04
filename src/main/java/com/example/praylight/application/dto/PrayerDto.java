package com.example.praylight.application.dto;
import com.example.praylight.application.service.UserService;
import com.example.praylight.domain.entity.User;
import lombok.*;
import com.example.praylight.domain.entity.Prayer;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
    private Long expiryDate;
    private Boolean isAnonymous;
    private Boolean isDeleted;
    private Boolean isVisible;

    private Long prayerRoomId;

    public Long getPrayerRoomId() {
        return this.prayerRoomId;
    }
    private List<Long> prayerRoomIds;  // 추가: 선택한 기도방의 ID 목록

    

public static PrayerDto from(Prayer prayer) {
    Long expiryDays = ChronoUnit.DAYS.between(prayer.getStartDate(), prayer.getExpiryDate());
    return PrayerDto.builder()
            .id(prayer.getId())
            .authorId(prayer.getAuthorId().getId())
            .content(prayer.getContent())
            .startDate(prayer.getStartDate())
            .expiryDate(expiryDays)  // 변경: 만료일 대신 만료일까지의 일 수를 넣어줍니다
            .isAnonymous(prayer.getIsAnonymous())
            .isDeleted(prayer.getIsDeleted())
            .isVisible(prayer.getIsVisible())
            .build();
}


}
