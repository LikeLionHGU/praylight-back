package com.example.praylight.domain.entity;

import com.example.praylight.application.dto.PrayerDto;
import com.example.praylight.application.service.MemberService;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @JoinColumn(name="author", nullable=false)
    private Member author;

//    private User author;

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
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    private List<PrayerRoomPrayer> prayerRoomPrayers = new ArrayList<>();

    public static Prayer from(PrayerDto dto, MemberService memberService) {
        Member author = memberService.getMemberById(dto.getAuthor());
        LocalDateTime startDate = dto.getStartDate();
        LocalDateTime expiryDate = startDate.plusDays(dto.getExpiryDate());
        return Prayer.builder()
                .id(dto.getId())
                .author(author)
                .build();
    }
}
