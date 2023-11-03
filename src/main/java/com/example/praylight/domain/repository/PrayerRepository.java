package com.example.praylight.domain.repository;

import com.example.praylight.domain.entity.Prayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PrayerRepository extends JpaRepository<Prayer, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Prayer p SET p.liked = ?2 WHERE p.id = ?1")
    void toggleLike(Long prayerId, boolean liked);
}
