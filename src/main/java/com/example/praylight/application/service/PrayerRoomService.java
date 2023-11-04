package com.example.praylight.application.service;

import com.example.praylight.application.dto.PrayerRoomDto;
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.domain.entity.User;
import com.example.praylight.domain.entity.UserPrayerRoom;
import com.example.praylight.domain.repository.PrayerRoomRepository;
import com.example.praylight.domain.repository.UserPrayerRoomRepository;
import com.example.praylight.presentation.request.CreatePrayerRoomRequest;
import com.example.praylight.presentation.response.CreatePrayerRoomResponse;
import com.example.praylight.presentation.response.ReadPrayerRoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrayerRoomService {
    private final PrayerRoomRepository prayerRoomRepository;
    private final UserService userService;
    private final UserPrayerRoomRepository userPrayerRoomRepository;

    @Autowired
    public PrayerRoomService(PrayerRoomRepository prayerRoomRepository, UserService userService, UserPrayerRoomRepository userPrayerRoomRepository) {
        this.prayerRoomRepository = prayerRoomRepository;
        this.userService = userService;
        this.userPrayerRoomRepository = userPrayerRoomRepository;
    }


    public CreatePrayerRoomResponse createPrayerRoom(CreatePrayerRoomRequest request) {
        PrayerRoom prayerRoom = PrayerRoom.builder()
                .authorId(request.getAuthorId())
                .title(request.getTitle())
                .lastActivityDate(request.getLastActivityDate())
                .isDeleted(request.getIsDeleted())
                .code(request.getCode())
                .isVisible(request.getIsVisible())
                .light(request.getLight())
                .build();

        PrayerRoom savedPrayerRoom = prayerRoomRepository.save(prayerRoom);

        return CreatePrayerRoomResponse.builder()
                .id(savedPrayerRoom.getId())
                .code(savedPrayerRoom.getCode())
                .build();
    }

    public ReadPrayerRoomResponse readPrayerRoom(String code) {
        Optional<PrayerRoom> optionalPrayerRoom = prayerRoomRepository.findByCode(code);

        if (optionalPrayerRoom.isPresent()) {
            PrayerRoom prayerRoom = optionalPrayerRoom.get();
            return ReadPrayerRoomResponse.builder()
                    .id(prayerRoom.getId())
                    .code(prayerRoom.getCode())
                    .message("기도방이 존재합니다.")
                    .build();
        } else {
            return ReadPrayerRoomResponse.builder()
                    .message("해당 기도방이 존재하지 않습니다.")
                    .build();
        }
    }

    public List<PrayerRoomDto> getPrayerRoomsByAuthorId(Long authorId) {
        List<PrayerRoom> prayerRooms = prayerRoomRepository.findByAuthorId(authorId);
        return prayerRooms.stream()
                .map(prayerRoom -> PrayerRoomDto.from(Optional.of(prayerRoom)))
                .collect(Collectors.toList());
    }

    @Transactional
    public void clickLight(Long userId, Long prayerRoomId) throws ChangeSetPersister.NotFoundException {
        UserPrayerRoom userPrayerRoom = userPrayerRoomRepository.findByUserIdAndPrayerRoomId(userId, prayerRoomId);
        if (userPrayerRoom == null) {
            // 사용자와 기도방 사이의 관계가 없으면 새로 생성
            User user = userService.getUserById(userId);
            PrayerRoom prayerRoom = prayerRoomRepository.findById(prayerRoomId).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
            userPrayerRoom = UserPrayerRoom.create(user, prayerRoom);
            userPrayerRoomRepository.save(userPrayerRoom);
        }

        if (!userPrayerRoom.getIsClicked()) {
            // 사용자가 불을 아직 클릭하지 않았으면 클릭 상태로 변경하고 light 값을 증가
            userPrayerRoom.setIsClicked(true);
            userPrayerRoom.getPrayerRoom().increaseLight();
        }
    }
}
