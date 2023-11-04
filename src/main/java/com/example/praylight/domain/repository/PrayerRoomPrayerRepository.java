package com.example.praylight.domain.repository;
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.domain.entity.PrayerRoomPrayer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrayerRoomPrayerRepository extends JpaRepository<PrayerRoomPrayer, Long>{

}

