
package com.example.praylight.presentation.controller;

import com.example.praylight.application.dto.MemberDto;
import com.example.praylight.application.dto.PrayerRoomDto;
import com.example.praylight.application.service.MemberService;
import com.example.praylight.application.service.PrayerRoomPrayerService;
import com.example.praylight.application.service.PrayerRoomService;
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.presentation.request.CreatePrayerRoomRequest;
import com.example.praylight.presentation.response.CreatePrayerRoomResponse;
import com.example.praylight.presentation.response.ReadPrayerRoomResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/rooms")
public class PrayerRoomController {
    HttpTransport transport = new NetHttpTransport();

    JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    @Value("${google.client-id}")
    private String CLIENT_ID;
    private final PrayerRoomService prayerRoomService;
    private boolean isAuthenticated(String idTokenString) {
        if (idTokenString.startsWith("Bearer ")) {
            idTokenString = idTokenString.substring("Bearer ".length());
        }
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (Exception e) {
            return false;
        }

        if (idToken != null) {
            return true;
        } else {
            return false;
        }
    }
    @GetMapping("/{roomId}/clicks")
    public ResponseEntity<Integer> getClicksCount(@PathVariable Long roomId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        int count = prayerRoomService.countClicks(roomId, startOfDay, endOfDay);

        return ResponseEntity.ok(count);
    }

    @PostMapping("/user/{userId}/code/{code}")
    public ResponseEntity<Void> enterPrayerRoom(@PathVariable Long userId,@PathVariable String code, @RequestHeader("Authorization") String token) {
        // 토큰에서 사용자 ID를 추출하는 로직이 필요합니다.
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        prayerRoomService.addMemberToPrayerRoom(code, userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PrayerRoomDto>> getPrayerRoomsByMember(@PathVariable Long userId) {
        List<PrayerRoomDto> prayerRooms = prayerRoomService.getPrayerRoomsByMember(userId);
        return ResponseEntity.ok(prayerRooms);
    }
    @GetMapping("/{roomId}/users")
    public ResponseEntity<List<MemberDto>> getMembersByPrayerRoom(@PathVariable Long roomId) {
        List<MemberDto> members = prayerRoomService.getMembersByPrayerRoom(roomId);
        return ResponseEntity.ok(members);
    }


    @Autowired
    public PrayerRoomController(PrayerRoomService prayerRoomService) {
        this.prayerRoomService = prayerRoomService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreatePrayerRoomResponse> createPrayerRoom(@RequestBody CreatePrayerRoomRequest request, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        CreatePrayerRoomRequest requestDto = new CreatePrayerRoomRequest();
        requestDto.setTitle(request.getTitle());
        requestDto.setAuthor(request.getAuthor());
        requestDto.setLastActivityDate(request.getLastActivityDate());
        requestDto.setCode(request.getCode());

        CreatePrayerRoomResponse response = prayerRoomService.createPrayerRoom(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PatchMapping("/{prayerRoomId}/user/{userId}/clickLight")
    public ResponseEntity<Void> clickLight(@PathVariable Long userId, @PathVariable Long prayerRoomId, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            prayerRoomService.clickLight(userId, prayerRoomId);
            return ResponseEntity.ok().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ReadPrayerRoomResponse> readPrayerRoom(@PathVariable String code, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ReadPrayerRoomResponse response = prayerRoomService.readPrayerRoom(code);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<PrayerRoomDto>> getPrayerRoomsByAuthor(@PathVariable Long userId, @RequestHeader("Authorization") String token) {
//        if(!isAuthenticated(token)) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        List<PrayerRoomDto> response = prayerRoomService.getPrayerRoomsByAuthor(userId);
//        return ResponseEntity.ok(response);
//    }



}