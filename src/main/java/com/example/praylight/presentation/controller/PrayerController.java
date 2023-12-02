package com.example.praylight.presentation.controller;
import com.example.praylight.application.service.PrayerRoomPrayerService;
import com.example.praylight.application.service.PrayerService;
import com.example.praylight.application.dto.PrayerDto;
import com.example.praylight.application.service.MemberService;
import com.example.praylight.domain.entity.Prayer;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;

import java.time.LocalDateTime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PrayerController {

    HttpTransport transport = new NetHttpTransport();
    JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    private final PrayerService prayerService;
    private final MemberService memberService;
    PrayerRoomPrayerService prayerRoomPrayerService;
    @Value("${google.client-id}")
    private String CLIENT_ID;

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
    @PostMapping("/room/prayer")
    public ResponseEntity<Long> save(@RequestBody PrayerDto request, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long savedId = prayerService.addPrayer(request);
        return ResponseEntity.ok(savedId);
    }

    @GetMapping("/prayer/user/{userId}")
    public ResponseEntity<List<PrayerDto>> getAllPrayersByUser(@PathVariable Long userId, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Prayer> prayers = prayerService.getAllPrayersByUser(userId);
        List<PrayerDto> prayerDtos = prayers.stream()
                .map(PrayerDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(prayerDtos);
    }

    @GetMapping("/prayers/user/{userId}/date/{date}")
    public ResponseEntity<List<Prayer>> getPrayersByAuthorAndDate(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Prayer> prayers = prayerService.getPrayersByAuthorAndDate(userId, date);
        return ResponseEntity.ok(prayers);
    }

    @GetMapping("/room/{prayerRoomId}/prayers")
    public ResponseEntity<List<Prayer>> getAllPrayersInPrayerRoom(@PathVariable Long prayerRoomId, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Prayer> prayers = prayerService.getAllPrayersInPrayerRoom(prayerRoomId);
        return ResponseEntity.ok(prayers);
    }


    @GetMapping("/room/{prayerRoomId}/prayers/date/{date}")
    public ResponseEntity<List<Prayer>> getPrayersByPrayerRoomAndDate(
            @PathVariable Long prayerRoomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Prayer> prayers = prayerService.getPrayersByPrayerRoomAndDate(prayerRoomId, date.toLocalDate());
        return ResponseEntity.ok(prayers);
    }

    @PatchMapping("/prayer/{prayerId}/user/{userId}/update")
    public ResponseEntity updatePrayer(@PathVariable Long prayerId, @PathVariable Long userId, @RequestBody PrayerDto dto, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        dto.setId(prayerId);
        Long updatedPrayerId = prayerService.updatePrayer(dto, userId);
        return ResponseEntity.ok().body(updatedPrayerId);
    }


    @PostMapping("/prayers/{prayerId}/user/{userId}/pray-together")
    public ResponseEntity<Boolean> togglePrayTogether(@PathVariable Long prayerId, @PathVariable Long userId, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        boolean isPrayTogetherCreated = prayerService.togglePrayTogether(userId, prayerId);
        return ResponseEntity.ok(isPrayTogetherCreated);
    }


    @GetMapping("/prayers/user/{userId}/pray-together")
    public ResponseEntity<List<Prayer>> getPrayersUserPrayedTogether(@PathVariable Long userId, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Prayer> prayers = prayerService.getPrayersUserPrayedTogether(userId);
        return ResponseEntity.ok(prayers);
    }


    @Transactional
    @PatchMapping("/prayer/{prayerId}/user/{userId}/delete")
    public ResponseEntity<Long> deleteById(@PathVariable Long prayerId, @PathVariable Long userId, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Long deletedPrayerId = prayerService.softDeletePrayer(prayerId, userId);
        return ResponseEntity.ok(deletedPrayerId);
    }


    @DeleteMapping("/room/{roomId}/prayer/{prayerId}/user/{userId}")
    public ResponseEntity<Void> removePrayerFromRoom(@PathVariable Long prayerId, @PathVariable Long roomId, @PathVariable Long userId, @RequestHeader("Authorization") String token) {
        if(!isAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        prayerRoomPrayerService.removePrayerFromPrayerRoom(prayerId, roomId, userId);
        return ResponseEntity.noContent().build();
    }



}



