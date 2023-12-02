package com.example.praylight.application.service;// PrayerRoomService.java
import com.example.praylight.domain.entity.PrayerRoomPrayer;
import com.example.praylight.domain.repository.PrayerRoomPrayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// PrayerRoomPrayerService.java
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PrayerRoomPrayerService {

    private final PrayerRoomPrayerRepository prayerRoomPrayerRepository;
    private final MemberService memberService;

    @Autowired
    public PrayerRoomPrayerService(PrayerRoomPrayerRepository prayerRoomPrayerRepository, MemberService memberService) {
        this.prayerRoomPrayerRepository = prayerRoomPrayerRepository;
        this.memberService = memberService;
    }

    public PrayerRoomPrayer save(PrayerRoomPrayer prayerRoomPrayer) {
        return prayerRoomPrayerRepository.save(prayerRoomPrayer);
    }

    @Transactional
    public void removePrayerFromPrayerRoom(Long prayerId, Long prayerRoomId, Long userId) {

        PrayerRoomPrayer prayerRoomPrayer = prayerRoomPrayerRepository.findByPrayerIdAndPrayerRoomId(prayerId, prayerRoomId);
        if (prayerRoomPrayer == null) {
            log.error("PrayerRoomPrayer is null");
            throw new ResourceNotFoundException("PrayerRoomPrayer", "prayerId and prayerRoomId", prayerId + " and " + prayerRoomId);
        }

        if (prayerRoomPrayer.getPrayer() == null) {
            log.error("Prayer is null");
            throw new ResourceNotFoundException("Prayer", "prayerId", prayerId.toString());
        } else if (prayerRoomPrayer.getPrayer().getAuthor() == null) {
            log.error("Prayer's author is null");
            throw new ResourceNotFoundException("AuthorId", "userId", userId.toString());
        }

        if (prayerRoomPrayer.getPrayerRoom() == null) {
            log.error("PrayerRoom is null");
            throw new ResourceNotFoundException("PrayerRoom", "roomId", prayerRoomId.toString());
        } else if (prayerRoomPrayer.getPrayerRoom().getAuthor() == null) {
            log.error("PrayerRoom's authorId is null");
            throw new ResourceNotFoundException("AuthorId", "userId", userId.toString());
        }

        if (!prayerRoomPrayer.getPrayer().getAuthor().getId().equals(userId)) {
            throw new UnauthorizedException("You are not the author of the prayer");
        }

        if (!prayerRoomPrayer.getPrayerRoom().getAuthor().getId().equals(userId)) {
            throw new UnauthorizedException("You are not the author of the prayer room");
        }

        prayerRoomPrayerRepository.delete(prayerRoomPrayer);
    }



    // 필요한 다른 메소드들을 여기에 추가하세요.
}

