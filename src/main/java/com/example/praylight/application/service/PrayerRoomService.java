package com.example.praylight.application.service;

import com.example.praylight.application.dto.PrayerRoomDto;
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.domain.repository.PrayerRoomRepository;
import com.example.praylight.presentation.request.CreatePrayerRoomRequest;
import com.example.praylight.presentation.response.CreatePrayerRoomResponse;
import com.example.praylight.presentation.response.ReadPrayerRoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrayerRoomService {
    private final PrayerRoomRepository prayerRoomRepository;

    @Autowired
    public PrayerRoomService(PrayerRoomRepository prayerRoomRepository) {
        this.prayerRoomRepository = prayerRoomRepository;
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

}
