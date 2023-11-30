package com.example.praylight.domain.repository;
import com.example.praylight.domain.entity.Member;
import com.example.praylight.domain.entity.Prayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface PrayerRepository extends JpaRepository<Prayer, Long>{
    List<Prayer> findAllByAuthor(Member author);
    List<Prayer> findByAuthorAndStartDateBetween(Member author, LocalDateTime start, LocalDateTime end);

}

