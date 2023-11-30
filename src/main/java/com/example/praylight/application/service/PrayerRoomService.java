package com.example.praylight.application.service;

import com.example.praylight.application.dto.MemberDto;
import com.example.praylight.application.dto.PrayerRoomDto;
import com.example.praylight.domain.entity.PrayerRoom;
import com.example.praylight.domain.entity.Member;
import com.example.praylight.domain.entity.MemberPrayerRoom;
import com.example.praylight.domain.repository.PrayerRoomRepository;
import com.example.praylight.domain.repository.MemberPrayerRoomRepository;
import com.example.praylight.presentation.request.CreatePrayerRoomRequest;
import com.example.praylight.presentation.response.CreatePrayerRoomResponse;
import com.example.praylight.presentation.response.ReadPrayerRoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrayerRoomService {
    private final PrayerRoomRepository prayerRoomRepository;
    private final MemberService memberService;
    private final MemberPrayerRoomRepository memberPrayerRoomRepository;

    @Autowired
    public PrayerRoomService(PrayerRoomRepository prayerRoomRepository, MemberService memberService, MemberPrayerRoomRepository memberPrayerRoomRepository) {
        this.prayerRoomRepository = prayerRoomRepository;
        this.memberService = memberService;
        this.memberPrayerRoomRepository = memberPrayerRoomRepository;
    }
    public PrayerRoom getPrayerRoomByCode(String code) {
        return prayerRoomRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("No prayer room found with code: " + code));
    }
    public int countClicks(Long prayerRoomId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return memberPrayerRoomRepository.countClicks(prayerRoomId, startOfDay, endOfDay);
    }
    public List<PrayerRoomDto> getPrayerRoomsByMember(Long memberId) {
        List<MemberPrayerRoom> memberPrayerRooms = memberPrayerRoomRepository.findByMemberId(memberId);
        return memberPrayerRooms.stream()
                .map(memberPrayerRoom -> PrayerRoomDto.from(Optional.of(memberPrayerRoom.getPrayerRoom())))
                .collect(Collectors.toList());
    }
    public List<MemberDto> getMembersByPrayerRoom(Long prayerRoomId) {
        List<MemberPrayerRoom> memberPrayerRooms = memberPrayerRoomRepository.findByPrayerRoomId(prayerRoomId);
        return memberPrayerRooms.stream()
                .map(memberPrayerRoom -> MemberDto.from(memberPrayerRoom.getMember()))
                .collect(Collectors.toList());
    }


    public void addMemberToPrayerRoom(String code, Long memberId) {
        Member member = memberService.getMemberById(memberId);
        PrayerRoom prayerRoom = getPrayerRoomByCode(code);

        // 이미 사용자가 해당 기도방에 추가되어 있는지 검사
        boolean isAlreadyAdded = memberPrayerRoomRepository.existsByMemberAndPrayerRoom(member, prayerRoom);
        if (isAlreadyAdded) {
            throw new RuntimeException("The member is already added to the prayer room.");
        }

        MemberPrayerRoom memberPrayerRoom = MemberPrayerRoom.create(member, prayerRoom);
        memberPrayerRoomRepository.save(memberPrayerRoom);
    }


    @Transactional
    public CreatePrayerRoomResponse createPrayerRoom(CreatePrayerRoomRequest request) {
        Boolean isDeleted = Optional.ofNullable(request.getIsDeleted()).orElse(false);
        Boolean isVisible = Optional.ofNullable(request.getIsDeleted()).orElse(false);
        Member author = memberService.getMemberById(request.getAuthor());
        String uniqueCode = UUID.randomUUID().toString(); // Unique한 코드 생성
        PrayerRoom prayerRoom = PrayerRoom.builder()
                .author(author)
                .title(request.getTitle())
                .lastActivityDate(request.getLastActivityDate())
                .isDeleted(isDeleted)
                .code(uniqueCode) // Unique한 코드로 설정
                .isVisible(isVisible)
                .light(0)
                .build();

        PrayerRoom savedPrayerRoom = prayerRoomRepository.save(prayerRoom);

        Member member = memberService.getMemberById(request.getAuthor());
        MemberPrayerRoom memberPrayerRoom = MemberPrayerRoom.create(member, prayerRoom);
        memberPrayerRoomRepository.save(memberPrayerRoom);

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

    public List<PrayerRoomDto> getPrayerRoomsByAuthor(Long author) {
        List<PrayerRoom> prayerRooms = prayerRoomRepository.findByAuthor(author);
        return prayerRooms.stream()
                .map(prayerRoom -> PrayerRoomDto.from(Optional.of(prayerRoom)))
                .collect(Collectors.toList());
    }

    @Transactional
    public void clickLight(Long userId, Long prayerRoomId) throws ChangeSetPersister.NotFoundException {
        MemberPrayerRoom memberPrayerRoom = memberPrayerRoomRepository.findByMemberIdAndPrayerRoomId(userId, prayerRoomId);

        // Set lastClicked to the current time
        memberPrayerRoom.setLastClicked(LocalDateTime.now());
    }

}