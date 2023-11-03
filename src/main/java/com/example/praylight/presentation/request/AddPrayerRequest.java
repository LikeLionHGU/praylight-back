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
    private Boolean liked;  // 추가: "좋아요" 버튼 상태를 나타내는 필드
}
