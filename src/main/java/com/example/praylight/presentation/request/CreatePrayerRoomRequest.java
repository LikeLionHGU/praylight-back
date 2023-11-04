package com.example.praylight.presentation.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreatePrayerRoomRequest {
    private Long authorId;
    private String title;
    private LocalDateTime lastActivityDate;
    private Boolean isDeleted;
    private String code;
    private Boolean isVisible;
    private Integer light;
}