package com.example.praylight.presentation.controller;
import com.example.praylight.application.service.PrayerService;
import com.example.praylight.application.dto.PrayerDto;
import com.example.praylight.application.service.UserService;
import com.example.praylight.domain.entity.Prayer;
import com.example.praylight.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PrayerController {

    private final PrayerService prayerService;
    private final UserService userService;

    @PostMapping("/prayerRoom/prayer")
    public ResponseEntity<Long> save(@RequestBody PrayerDto request) {
        Long savedId = prayerService.addPrayer(request);
        return ResponseEntity.ok(savedId);
    }



    //    @GetMapping("/prayer/{prayerId}")
//    public ResponseEntity<PrayerDto> getOnePrayer(@PathVariable Long prayerId) {
//        Prayer prayer = prayerService.getOnePrayer(prayerId);
//        return ResponseEntity.ok(PrayerDto.from(prayer));
//    }
//
//    @GetMapping("/prayers")
//    public ResponseEntity<List<PrayerDto>> getAllPrayers() {
//        List<Prayer> prayers = prayerService.getAllPrayers();
//        List<PrayerDto> response = prayers.stream()
//                .map(PrayerDto::from)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(response);
//    }
    //todo 유저랑 기도방도 추가해서 넣어야 함
    @Transactional
    @PatchMapping("/prayerRoom/prayer/delete")
    public ResponseEntity<Void> deleteById(@RequestParam Long prayerId,  User user) {
        prayerService.softDeletePrayer(prayerId, user);
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
