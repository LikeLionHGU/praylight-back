package com.example.praylight.domain.entity;

import com.example.praylight.application.dto.PrayerDto;
import lombok.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import com.example.praylight.domain.entity.common.BaseEntity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE prayer SET deleted = true WHERE id = ?")
public class Prayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorId;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private Boolean isAnonymous;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private Boolean isVisible;

    public static Prayer from(PrayerDto dto) {
        return Prayer.builder()
                .id(dto.getId())
                .authorId(dto.getAuthorId())
                .content(dto.getContent())
                .startDate(dto.getStartDate())
                .expiryDate(dto.getExpiryDate())
                .isAnonymous(dto.getIsAnonymous() != null ? dto.getIsAnonymous() : false)
                .isDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false)
                .isVisible(dto.getIsVisible() != null ? dto.getIsVisible() : false)
                .build();
    }

}

