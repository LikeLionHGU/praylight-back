package com.example.praylight.domain.repository;
import com.example.praylight.domain.entity.Prayer;
import com.example.praylight.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface PrayerRepository extends JpaRepository<Prayer, Long>{
    List<Prayer> findAllByAuthor(User author);
    List<Prayer> findByAuthor_IdAndStartDateBetween(Long authorId, LocalDateTime start, LocalDateTime end);





}

