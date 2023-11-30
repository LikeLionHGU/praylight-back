package com.example.praylight.domain.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrayTogether {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="member_id", nullable=false)
    private Member member;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="prayer_id", nullable=false)
    private Prayer prayer;

}