package com.example.praylight.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatePrayerRoomResponse {
    private Long id;
    private String code;
}
