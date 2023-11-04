package com.example.praylight.domain.repository;
import com.example.praylight.domain.entity.Prayer;
import com.example.praylight.domain.entity.PrayerRoom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrayerRoomRepository extends JpaRepository<PrayerRoom, Long>{

}

