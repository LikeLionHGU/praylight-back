package com.example.praylight.domain.repository;

import com.example.praylight.domain.entity.UserPrayerRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPrayerRoomRepository extends JpaRepository<UserPrayerRoom, Long> {
    UserPrayerRoom findByUserIdAndPrayerRoomId(Long userId, Long prayerRoomId);
}
