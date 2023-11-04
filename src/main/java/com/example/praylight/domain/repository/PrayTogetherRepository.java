package com.example.praylight.domain.repository;
import com.example.praylight.domain.entity.PrayTogether;
import com.example.praylight.domain.entity.Prayer;
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PrayTogetherRepository extends JpaRepository<PrayTogether, Long>{

    PrayTogether findByUserAndPrayer(User user, Prayer prayer);
    List<PrayTogether> findAllByUser(User user);

}

