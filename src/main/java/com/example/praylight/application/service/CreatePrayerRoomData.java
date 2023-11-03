package com.example.praylight.application.service;

public class CreatePrayerRoomData {
    private Long prayerRoomId; // 변수 이름 수정
    private String code;

    public CreatePrayerRoomData(Long prayerRoomId, String code) {
        this.prayerRoomId = prayerRoomId;
        this.code = code;
    }

    // Getter and Setter 생성
    public Long getPrayerRoomId() {
        return prayerRoomId;
    }

    public void setPrayerRoomId(Long prayerRoomId) {
        this.prayerRoomId = prayerRoomId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}


