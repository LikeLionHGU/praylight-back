package com.example.praylight.presentation.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPrayerRequest {
    private Long authorId;
    private String content;
    private String startDate;
    private String expiryDate;
}