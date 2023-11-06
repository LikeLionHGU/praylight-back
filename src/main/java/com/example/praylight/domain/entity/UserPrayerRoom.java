package com.example.praylight.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_prayer_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserPrayerRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "prayer_room_id")
    private PrayerRoom prayerRoom;

    @Column(name = "is_clicked")
    private Boolean isClicked = false;

    public static UserPrayerRoom create(User user, PrayerRoom prayerRoom) {
        UserPrayerRoom userPrayerRoom = new UserPrayerRoom();
        userPrayerRoom.setUser(user);
        userPrayerRoom.setPrayerRoom(prayerRoom);
        return userPrayerRoom;
    }
}
