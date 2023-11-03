package com.example.praylight.application.service;

import com.example.praylight.domain.entity.Prayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.praylight.domain.repository.PrayerRepository;
import java.util.List;
import com.example.praylight.application.dto.PrayerDto;

@Service
@RequiredArgsConstructor
public class PrayerService {
    private final PrayerRepository prayerRepository;

    @Transactional
    public Long addPrayer(PrayerDto dto){
        Prayer newPrayer = prayerRepository.save(Prayer.from(dto));
        return newPrayer.getId();
    }

    @Transactional
    public Prayer getOnePrayer(Long prayerId){
        Prayer prayer = prayerRepository.findById(prayerId).orElseThrow(() -> new IllegalArgumentException("no such prayer"));
        return prayer;
    }

    @Transactional
    public List<Prayer> getAllPrayers(){
        List<Prayer> prayerList = prayerRepository.findAll();
        return prayerList;
    }

//    @Transactional
//    public void deletePrayer(Long prayerId){
//        prayerRepository.deleteById(prayerId);
//    }
    public void softDeletePrayer(Long prayerId) {
        Prayer prayer = prayerRepository.findById(prayerId)
                .orElseThrow(() -> new ResourceNotFoundException("Prayer", "id", prayerId));
        prayer.setIsDeleted(true);
        prayerRepository.save(prayer);
    }

//    public void togetherPrayer(Long prayerId) {
//        Prayer prayer = prayerRepository.findById(prayerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Prayer", "id", prayerId));
//        prayer.setIsDeleted(true);
//        prayerRepository.save(prayer);
//    }


    @Transactional
    public Long changePrayer(PrayerDto dto){
        Prayer prayer = prayerRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("no such prayer"));
        // 여기서는 dto로부터 받은 정보로 기도의 내용을 변경하면 됩니다.
        // 예를 들어, 다음과 같이 작성할 수 있습니다:
        // prayer.setContent(dto.getContent());
        // prayer.setExpiryDate(dto.getExpiryDate());
        // ... (나머지 필드도 이와 같이 설정)

        Prayer updatedPrayer = prayerRepository.save(prayer);
        return updatedPrayer.getId();
    }
}

