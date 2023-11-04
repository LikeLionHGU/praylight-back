package com.example.praylight.presentation.request;

import lombok.Getter;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Date;

public class CreatePrayerRoomRequest {
    private Long authorId;
    private String title;
private Long authorId;
private LocalDateTime lastActivityDate;
private Boolean isDeleted;
private String code;
private Boolean isVisible;
private Integer light;

}
