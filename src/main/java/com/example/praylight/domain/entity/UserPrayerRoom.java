package com.example.praylight.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_prayer_room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrayerRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
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
