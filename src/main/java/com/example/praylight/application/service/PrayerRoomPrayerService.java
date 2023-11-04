package com.example.praylight.application.service;// PrayerRoomService.java
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.domain.entity.PrayerRoomPrayer;
import com.example.praylight.domain.repository.PrayerRoomPrayerRepository;
import com.example.praylight.domain.repository.PrayerRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// PrayerRoomPrayerService.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrayerRoomPrayerService {

    private final PrayerRoomPrayerRepository prayerRoomPrayerRepository;

    @Autowired
    public PrayerRoomPrayerService(PrayerRoomPrayerRepository prayerRoomPrayerRepository) {
        this.prayerRoomPrayerRepository = prayerRoomPrayerRepository;
    }

    public PrayerRoomPrayer save(PrayerRoomPrayer prayerRoomPrayer) {
        return prayerRoomPrayerRepository.save(prayerRoomPrayer);
    }

    // 필요한 다른 메소드들을 여기에 추가하세요.
}

