package com.example.praylight.presentation.controller;
import com.example.praylight.application.service.PrayerService;
import com.example.praylight.application.dto.PrayerDto;
import com.example.praylight.application.service.UserService;
import com.example.praylight.domain.entity.Prayer;
import com.example.praylight.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class PrayerController {

    private final PrayerService prayerService;
    private final UserService userService;

    @PostMapping("/prayer")
    public ResponseEntity<Long> save(@RequestBody PrayerDto request) {
        Long savedId = prayerService.addPrayer(request);
        return ResponseEntity.ok(savedId);
    }

    @GetMapping("/prayer/user/{userId}")
    public ResponseEntity<List<PrayerDto>> getAllPrayersByUser(@PathVariable Long userId) {
        List<Prayer> prayers = prayerService.getAllPrayersByUser(userId);
        List<PrayerDto> prayerDtos = prayers.stream()
                .map(PrayerDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(prayerDtos);
    }

    @GetMapping("/prayers/author/{authorId}/date/{date}")
    public ResponseEntity<List<Prayer>> getPrayersByAuthorAndDate(
            @PathVariable Long authorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<Prayer> prayers = prayerService.getPrayersByAuthorAndDate(authorId, date);
        return ResponseEntity.ok(prayers);
    }

    @PatchMapping("/prayer/{id}")
    public ResponseEntity updatePrayer(@PathVariable Long id, @RequestBody PrayerDto dto) {
        dto.setId(id); // URL에서 받아온 id를 dto에 설정
        Long updatedPrayerId = prayerService.updatePrayer(dto);
        return ResponseEntity.ok().body("Prayer updated with ID: " + updatedPrayerId);
    }




    @PostMapping("/prayers/{prayerId}/pray-together")
    public ResponseEntity<Void> togglePrayTogether(@PathVariable Long prayerId, @RequestParam Long userId) {
        prayerService.togglePrayTogether(userId, prayerId);
        return ResponseEntity.ok().build();
    }

    //내가 함께하고 있는 모든 목록 추가
    @GetMapping("/prayers/user/{userId}/pray-together")
    public ResponseEntity<List<Prayer>> getPrayersUserPrayedTogether(@PathVariable Long userId) {
        List<Prayer> prayers = prayerService.getPrayersUserPrayedTogether(userId);
        return ResponseEntity.ok(prayers);
    }


    //todo 유저랑 기도방도 추가해서 넣어야 함
    @Transactional
    @PatchMapping("/prayerRoom/prayer/{prayerId}/delete")
    public ResponseEntity<Void> deleteById(@PathVariable Long prayerId, @RequestParam Long userId) {
        prayerService.softDeletePrayer(prayerId, userId);
        return ResponseEntity.ok(null);
    }



//    @PatchMapping("/prayerRoom/prayer/together")
//    public ResponseEntity<Void> togetherById(@RequestParam Long prayerId) {
//        prayerService.togetherPrayer(prayerId);
//        return ResponseEntity.ok(null);
//    }
//
//    @PatchMapping("/prayer")
//    public ResponseEntity<Void> changePrayer (@RequestBody PrayerDto request) {
//        Long updatedId = prayerService.changePrayer(request);
//        return ResponseEntity.ok(null);
//    }
}
