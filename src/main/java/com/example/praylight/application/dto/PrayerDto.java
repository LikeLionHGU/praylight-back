package com.example.praylight.application.dto;
import com.example.praylight.domain.entity.User;
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


    public static Prayer from(PrayerDto dto, User author) {
        return Prayer.builder()
                .id(dto.getId())
                .authorId(author)
                .content(dto.getContent())
                .startDate(dto.getStartDate())
                .expiryDate(dto.getExpiryDate())
                .isAnonymous(dto.getIsAnonymous() != null ? dto.getIsAnonymous() : false)
                .isDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false)
                .isVisible(dto.getIsVisible() != null ? dto.getIsVisible() : false)
                .build();
    }



}
