package com.example.praylight.application.service;

import lombok.Getter;

@Getter
public class CreatePrayerRoomData {
    private Long PrayerRoomId;
    private String code;

    public CreatePrayerRoomData(Long prayerRoomId, String code) {
        this.PrayerRoomId = prayerRoomId;
        this.code = code;
    }

    // Getter and Setter
}
