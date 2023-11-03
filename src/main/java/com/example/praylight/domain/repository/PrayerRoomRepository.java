package com.example.praylight.domain.repository;

import com.example.praylight.domain.entity.PrayerRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrayerRoomRepository extends JpaRepository<PrayerRoom, Long> {
    PrayerRoom findByCode(String code);
    List<PrayerRoom> findByUserId(Long authorId);
}
