package com.example.praylight.presentation.controller;
import com.example.praylight.application.service.PrayerService;
import com.example.praylight.application.dto.PrayerDto;
import com.example.praylight.domain.entity.Prayer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PrayerController {

    private final PrayerService prayerService;

    @PostMapping("/prayerRoom/prayer")
    public ResponseEntity<Long> save(@RequestBody PrayerDto request) {
        Long savedId = prayerService.addPrayer(request);
        return ResponseEntity.ok(savedId);
    }

    @GetMapping("/prayers")
    public ResponseEntity<List<PrayerDto>> getAllPrayers() {
        List<Prayer> prayers = prayerService.getAllPrayers();
        List<PrayerDto> response = prayers.stream()
                .map(PrayerDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // 추가: "좋아요" 토글 엔드포인트
    @PatchMapping("/prayers/toggleLike/{prayerId}")
    public ResponseEntity<Void> toggleLike(@PathVariable Long prayerId, @RequestParam Boolean liked) {
        prayerService.toggleLike(prayerId, liked);
        return ResponseEntity.ok(null);
    }
}
