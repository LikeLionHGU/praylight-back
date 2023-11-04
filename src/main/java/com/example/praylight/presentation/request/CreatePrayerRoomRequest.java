package com.example.praylight.presentation.request;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class CreatePrayerRoomRequest {
    private String title;
    private Long authorId;
    private LocalDateTime lastActivityDate;
    private String code;

//    public String getTitle() {
//        return title;
//    }

    // Getter and Setter for title
}
