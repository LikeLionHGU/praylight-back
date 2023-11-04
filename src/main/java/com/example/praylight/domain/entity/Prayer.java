package com.example.praylight.domain.entity;

import com.example.praylight.application.dto.PrayerDto;
import com.example.praylight.application.service.PrayerRoomPrayerService;
import com.example.praylight.application.service.PrayerRoomService;
import com.example.praylight.application.service.UserService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE prayer SET deleted = true WHERE id = ?")
public class Prayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="author_id", nullable=false)
    private User author;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private Boolean isAnonymous;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private Boolean isVisible;

    @OneToMany(mappedBy = "prayer")
    @JsonManagedReference
    private List<PrayTogether> likes = new ArrayList<>();

    @OneToMany(mappedBy = "prayer")
    private List<PrayerRoomPrayer> prayerRoomPrayers = new ArrayList<>();

//    public static Prayer from(PrayerDto dto, UserService userService) {
//        User author = userService.getUserById(dto.getAuthorId());
//        return Prayer.builder()
//                .id(dto.getId())
//                .author(author)  // 'authorId' 대신 'author'를 사용
//                .content(dto.getContent())
//                .startDate(dto.getStartDate())
//                .expiryDate(dto.getExpiryDate())
//                .isAnonymous(dto.getIsAnonymous() != null ? dto.getIsAnonymous() : false)
//                .isDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false)
//                .isVisible(dto.getIsVisible() != null ? dto.getIsVisible() : false)
//                .build();
//    }

    // Prayer.java
    public static Prayer from(PrayerDto dto, UserService userService, PrayerRoomService prayerRoomService, PrayerRoomPrayerService prayerRoomPrayerService) {
        User author = userService.getUserById(dto.getAuthorId());
        PrayerRoom prayerRoom = prayerRoomService.getPrayerRoomById(dto.getPrayerRoomId());
        LocalDateTime startDate = dto.getStartDate();
        LocalDateTime expiryDate = startDate.plusDays(dto.getExpiryDate()); // 만료일을 계산합니다
        Prayer prayer = Prayer.builder()
                .id(dto.getId())
                .author(author)
                .content(dto.getContent())
                .startDate(startDate)
                .expiryDate(expiryDate)  // 계산된 만료일을 설정합니다
                .isAnonymous(dto.getIsAnonymous() != null ? dto.getIsAnonymous() : false)
                .isDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false)
                .isVisible(dto.getIsVisible() != null ? dto.getIsVisible() : false)
                .build();

        // PrayerRoomPrayer 엔티티 생성 및 저장
        PrayerRoomPrayer prayerRoomPrayer = new PrayerRoomPrayer();
        prayerRoomPrayer.setPrayer(prayer);
        prayerRoomPrayer.setPrayerRoom(prayerRoom);
        prayerRoomPrayerService.save(prayerRoomPrayer);

        return prayer;
    }


    public static Prayer from(PrayerDto dto, UserService userService) {
        User author = userService.getUserById(dto.getAuthorId());
        LocalDateTime startDate = dto.getStartDate();
        LocalDateTime expiryDate = startDate.plusDays(dto.getExpiryDate());
        return Prayer.builder()
                .id(dto.getId())
                .author(author)
                .content(dto.getContent())
                .startDate(startDate)
                .expiryDate(expiryDate)  // 변경: 만료일을 계산해서 넣어줍니다
                .isAnonymous(dto.getIsAnonymous() != null ? dto.getIsAnonymous() : false)
                .isDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false)
                .isVisible(dto.getIsVisible() != null ? dto.getIsVisible() : false)
                .build();
    }




}
