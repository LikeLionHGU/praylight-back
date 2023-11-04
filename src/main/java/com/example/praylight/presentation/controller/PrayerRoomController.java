package com.example.praylight.presentation.controller;

import com.example.praylight.presentation.request.CreatePrayerRoomRequest;
import com.example.praylight.presentation.response.CreatePrayerRoomResponse;
import com.example.praylight.presentation.response.ReadPrayerRoomResponse;
import com.example.praylight.application.service.PrayerRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prayer-rooms")
public class PrayerRoomController {
    private final PrayerRoomService prayerRoomService;

    @Autowired
    public PrayerRoomController(PrayerRoomService prayerRoomService) {
        this.prayerRoomService = prayerRoomService;
    }

    @PostMapping
    public ResponseEntity<CreatePrayerRoomResponse> createPrayerRoom(@RequestBody CreatePrayerRoomRequest request) {
        CreatePrayerRoomResponse response = prayerRoomService.createPrayerRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ReadPrayerRoomResponse> readPrayerRoom(@PathVariable String code) {
        ReadPrayerRoomResponse response = prayerRoomService.readPrayerRoom(code);
        return ResponseEntity.ok(response);
    }
}
