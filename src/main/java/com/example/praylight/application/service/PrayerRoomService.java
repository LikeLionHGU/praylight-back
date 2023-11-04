
package com.example.praylight.application.service;

import com.example.praylight.application.dto.PrayerRoomDto;
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.domain.repository.PrayerRoomRepository;
import com.example.praylight.presentation.response.CreatePrayerRoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class PrayerRoomService {

    private final PrayerRoomRepository prayerRoomRepository;

    @Autowired
    public PrayerRoomService(PrayerRoomRepository prayerRoomRepository) {
        this.prayerRoomRepository = prayerRoomRepository;
    }


    @Transactional
    public CreatePrayerRoomResponse createPrayerRoom(PrayerRoomDto requestDto) {
        // PrayerRoomDto를 PrayerRoom 엔티티로 변환
        PrayerRoom prayerRoom = PrayerRoom.from(requestDto);

        // PrayerRoom 저장
        PrayerRoom savedPrayerRoom = prayerRoomRepository.save(prayerRoom);

        // 응답 데이터 생성
        CreatePrayerRoomResponse response = new CreatePrayerRoomResponse();
        response.setStatus("success");
        response.setMessage("기도방이 성공적으로 생성되었습니다.");
        response.setData(new CreatePrayerRoomData(savedPrayerRoom.getId(), savedPrayerRoom.getCode()));

        return response;
    }

    @Transactional
    public CreatePrayerRoomResponse getPrayerRoomByCode(String roomCode) {
        // 방 코드로 해당 방을 검색
        PrayerRoom prayerRoom = prayerRoomRepository.findByCode(roomCode);

        if (prayerRoom == null) {
            // 방이 없는 경우 예외를 던지거나 다른 방법으로 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "방을 찾을 수 없습니다.");
        }

        // 방이 있는 경우 응답 데이터 생성
        CreatePrayerRoomResponse response = new CreatePrayerRoomResponse();
        response.setStatus("success");
        response.setMessage("찾으시는 기도방이 있습니다.");
        response.setData(new CreatePrayerRoomData(prayerRoom.getId(), prayerRoom.getCode()));

        return response;
    }

    @Transactional
    public List<PrayerRoom> getPrayerRoomsByAuthorId(Long userId) {
        // 사용자 ID를 기반으로 해당 사용자가 속한 기도방 목록을 검색
        List<PrayerRoom> prayerRooms = prayerRoomRepository.findByAuthorId(userId);

        if (prayerRooms.isEmpty()) {
            // 사용자가 속한 기도방이 없는 경우 예외를 던지거나 다른 방법으로 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자가 속한 기도방이 없습니다.");
        }

        return prayerRooms;
    }



}
