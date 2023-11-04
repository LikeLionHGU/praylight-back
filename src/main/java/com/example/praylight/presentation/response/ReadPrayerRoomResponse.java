package com.example.praylight.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadPrayerRoomResponse {
    private Long id;
    private String code;
    private String message;
}
