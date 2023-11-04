package com.example.praylight.application.service;

import com.example.praylight.domain.entity.PrayTogether;
import com.example.praylight.domain.entity.Prayer;
import com.example.praylight.domain.entity.User;
import com.example.praylight.domain.repository.PrayTogetherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.praylight.domain.repository.PrayerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.praylight.application.dto.PrayerDto;

@Service
@RequiredArgsConstructor
public class PrayerService {
    private final PrayerRepository prayerRepository;
    private final PrayTogetherRepository prayTogetherRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public List<Prayer> getAllPrayersByUser(Long userId){
        User user = userService.getUserById(userId);
        List<Prayer> prayerList = prayerRepository.findAllByAuthor(user);
        return prayerList.stream()
                .filter(prayer -> !prayer.getIsDeleted() && prayer.getExpiryDate().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    public List<Prayer> getPrayersByAuthorAndDate(Long authorId, LocalDateTime date) {
        LocalDateTime start = date.toLocalDate().atStartOfDay(); // 그 날의 00:00
        LocalDateTime end = start.plusDays(1).minusSeconds(1); // 그 날의 23:59:59
        List<Prayer> prayers = prayerRepository.findByAuthor_IdAndStartDateBetween(authorId, start, end);
        return prayers.stream()
                .filter(prayer -> !prayer.getIsDeleted())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Prayer> getPrayersUserPrayedTogether(Long userId) {
        User user = userService.getUserById(userId);
        List<PrayTogether> prayTogethers = prayTogetherRepository.findAllByUser(user);

        return prayTogethers.stream()
                .map(PrayTogether::getPrayer)
                .collect(Collectors.toList());
    }

    @Transactional
    public void togglePrayTogether(Long userId, Long prayerId) {
        User user = userService.getUserById(userId);
        Prayer prayer = getOnePrayer(prayerId);
        PrayTogether prayTogether = prayTogetherRepository.findByUserAndPrayer(user, prayer);

        if (prayTogether != null) {
            // 유저가 이미 이 기도를 함께하고 있다면, 함께 기도하기 정보를 삭제합니다.
            prayTogetherRepository.delete(prayTogether);
        } else {
            // 유저가 이 기도를 함께하고 있지 않다면, 함께 기도하기 정보를 추가합니다.
            prayTogether = PrayTogether.builder()
                    .user(user)
                    .prayer(prayer)
                    .build();
            prayTogetherRepository.save(prayTogether);
        }
    }





    @Transactional
    public Long addPrayer(PrayerDto dto){
        Long authorId = dto.getAuthorId();

        if (authorId == null) {
            throw new IllegalArgumentException("Author ID must not be null");
        }
        User author = userService.getUserById(authorId);
        LocalDateTime startDate = LocalDateTime.now(); // 작성일을 현재 일시로 설정
        LocalDateTime expiryDate = startDate.plusDays(dto.getExpiryDate()); // 만료일을 작성일로부터 사용자가 지정한 일 수만큼 더한 날짜로 설정
        Prayer newPrayer = Prayer.builder()
                .id(dto.getId())
                .author(author)  // 'authorId' 대신 'author'를 사용
                .content(dto.getContent())
                .startDate(dto.getStartDate())
                .expiryDate(expiryDate)
                .isAnonymous(dto.getIsAnonymous() != null ? dto.getIsAnonymous() : false)
                .isDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false)
                .isVisible(dto.getIsVisible() != null ? dto.getIsVisible() : false)
                .build();
        newPrayer = prayerRepository.save(newPrayer);
        return newPrayer.getId();
    }

    @Transactional
    public Long updatePrayer(PrayerDto dto) {
        Long prayerId = dto.getId();

        // 기도를 찾지 못한 경우 예외를 던집니다.
        Prayer prayer = prayerRepository.findById(prayerId)
                .orElseThrow(() -> new IllegalArgumentException("No such prayer"));

        // 기도 정보를 수정합니다.
        prayer.setContent(dto.getContent());
        prayer.setExpiryDate(prayer.getStartDate().plusDays(dto.getExpiryDate()));
        // 만료일을 작성일로부터 사용자가 지정한 일 수만큼 더한 날짜로 설정
        prayer.setIsAnonymous(dto.getIsAnonymous() != null ? dto.getIsAnonymous() : false);
        prayer.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);
        prayer.setIsVisible(dto.getIsVisible() != null ? dto.getIsVisible() : false);

        // 변경된 정보를 저장하고 기도 ID를 반환합니다.
        Prayer updatedPrayer = prayerRepository.save(prayer);
        return updatedPrayer.getId();
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
public void softDeletePrayer(Long prayerId, Long userId) {  // 'User' 대신 'Long'을 사용
    Prayer prayer = prayerRepository.findById(prayerId)
            .orElseThrow(() -> new ResourceNotFoundException("Prayer", "id", prayerId));
    User user = userService.getUserById(userId);  // 'userId'로 'User' 객체를 찾음

    // 게시물의 작성자가 현재 사용자인지 확인
    if (!prayer.getAuthor().getId().equals(user.getId())) {  // 'getAuthorId()' 대신 'getAuthor().getId()'를 사용
        throw new UnauthorizedException("You are not authorized to delete this prayer.");
    }

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

