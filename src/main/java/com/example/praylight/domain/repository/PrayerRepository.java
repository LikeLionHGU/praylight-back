package com.example.praylight.domain.repository;
import com.example.praylight.domain.entity.Prayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface PrayerRepository extends JpaRepository<Prayer, Long>{

}

