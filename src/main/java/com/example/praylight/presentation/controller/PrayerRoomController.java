package com.example.praylight.presentation.controller;

import com.example.praylight.application.dto.PrayerRoomDto;
import com.example.praylight.application.service.PrayerRoomService;
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.presentation.request.CreatePrayerRoomRequest;
import com.example.praylight.presentation.response.CreatePrayerRoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prayer-rooms")
public class PrayerRoomController {

    private final PrayerRoomService prayerRoomService;

    @Autowired
    public PrayerRoomController(PrayerRoomService prayerRoomService) {
        this.prayerRoomService = prayerRoomService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreatePrayerRoomResponse> createPrayerRoom(@RequestBody CreatePrayerRoomRequest request) {
        PrayerRoomDto requestDto = new PrayerRoomDto();
        requestDto.setTitle(request.getTitle());  // Assuming title is a field in CreatePrayerRoomRequest
        // You can set other fields similarly based on your request structure.

        CreatePrayerRoomResponse response = prayerRoomService.createPrayerRoom(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getByCode/{code}")
    public ResponseEntity<CreatePrayerRoomResponse> getPrayerRoomByCode(@PathVariable String code) {
        CreatePrayerRoomResponse response = prayerRoomService.getPrayerRoomByCode(code);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getByUser/{authorId}")
    public ResponseEntity<List<PrayerRoom>> getPrayerRoomsByUserId(@PathVariable Long authorId) {
        List<PrayerRoom> prayerRooms = prayerRoomService.getPrayerRoomsByUserId(authorId);
        return ResponseEntity.ok(prayerRooms);
    }
}
