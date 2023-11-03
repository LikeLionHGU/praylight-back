package com.example.praylight.domain.entity;
import com.example.praylight.application.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="prayer_id", nullable=false)
    private Prayer prayer;

}