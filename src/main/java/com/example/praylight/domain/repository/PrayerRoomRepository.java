package com.example.praylight.domain.repository;


import com.example.praylight.domain.entity.PrayerRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrayerRoomRepository extends JpaRepository<PrayerRoom, Long> {

  Optional<PrayerRoom> findByCode(String code);

    List<PrayerRoom> findByAuthorId(Long authorId);
}

