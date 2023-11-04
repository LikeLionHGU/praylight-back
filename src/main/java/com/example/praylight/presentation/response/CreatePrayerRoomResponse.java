package com.example.praylight.presentation.response;

import com.example.praylight.application.service.CreatePrayerRoomData;


public class CreatePrayerRoomResponse {
    private String status;
    private String message;
    private CreatePrayerRoomData data;

    // 생성자, Getter 및 Setter 메서드

    public CreatePrayerRoomResponse() {
        // 기본 생성자
    }

    public CreatePrayerRoomResponse(String status, String message, CreatePrayerRoomData data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CreatePrayerRoomData getData() {
        return data;
    }

    public void setData(CreatePrayerRoomData data) {
        this.data = data;
    }


}

