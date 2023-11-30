package com.example.praylight.domain.repository;

import com.example.praylight.domain.entity.Member;
import com.example.praylight.domain.entity.MemberPrayerRoom;
import com.example.praylight.domain.entity.PrayerRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberPrayerRoomRepository extends JpaRepository<MemberPrayerRoom, Long> {
    MemberPrayerRoom findByMemberIdAndPrayerRoomId(Long userId, Long prayerRoomId);
    boolean existsByMemberAndPrayerRoom(Member member, PrayerRoom prayerRoom);
    List<MemberPrayerRoom> findByMemberId(Long memberId);
    List<MemberPrayerRoom> findByPrayerRoomId(Long prayerRoomId);

    @Query("SELECT COUNT(m) FROM MemberPrayerRoom m WHERE m.prayerRoom.id = :prayerRoomId AND m.lastClicked BETWEEN :startOfDay AND :endOfDay")
    int countClicks(@Param("prayerRoomId") Long prayerRoomId, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);


}
