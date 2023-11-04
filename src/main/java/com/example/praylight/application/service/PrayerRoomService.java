package com.example.praylight.application.service;// PrayerRoomService.java
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.domain.repository.PrayerRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrayerRoomService {

    private final PrayerRoomRepository prayerRoomRepository;

    @Autowired
    public PrayerRoomService(PrayerRoomRepository prayerRoomRepository) {
        this.prayerRoomRepository = prayerRoomRepository;
    }

    public PrayerRoom getPrayerRoomById(Long id) {
        return prayerRoomRepository.findById(id).orElse(null);
    }

    // 필요한 다른 메소드들을 여기에 추가하세요.
}
