package com.example.praylight.presentation.controller;// PrayerRoomController.java
import com.example.praylight.application.dto.PrayerDto;
import com.example.praylight.application.service.PrayerRoomService;
import com.example.praylight.domain.entity.PrayerRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prayerRooms")
public class PrayerRoomController {

    private final PrayerRoomService prayerRoomService;

    @Autowired
    public PrayerRoomController(PrayerRoomService prayerRoomService) {
        this.prayerRoomService = prayerRoomService;
    }

//    @PostMapping("/room-list/room")
//    public ResponseEntity<Long> save(@RequestBody PrayerDto request) {
//        Long savedId = prayerRoomService.addPrayer(request);
//        return ResponseEntity.ok(savedId);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<PrayerRoom> getPrayerRoomById(@PathVariable Long id) {
        PrayerRoom prayerRoom = prayerRoomService.getPrayerRoomById(id);
        if (prayerRoom == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(prayerRoom);
        }
    }

    // 필요한 다른 엔드포인트들을 여기에 추가하세요.
}
