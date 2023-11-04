package com.example.praylight.domain.repository;
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.domain.entity.PrayerRoomPrayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface PrayerRoomPrayerRepository extends JpaRepository<PrayerRoomPrayer, Long>{
    List<PrayerRoomPrayer> findAllByPrayerRoom(PrayerRoom prayerRoom);

    @Query("SELECT p FROM PrayerRoomPrayer p WHERE p.prayerRoom = :prayerRoom AND p.prayer.startDate BETWEEN :startOfDay AND :endOfDay AND p.prayer.isDeleted = false")
    List<PrayerRoomPrayer> findAllByPrayerRoomAndDate(@Param("prayerRoom") PrayerRoom prayerRoom, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

}


