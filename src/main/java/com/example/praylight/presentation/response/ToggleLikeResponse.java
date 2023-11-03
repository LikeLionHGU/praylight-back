package com.example.praylight.presentation.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToggleLikeResponse {
    private String status;
    private String message;
    private boolean liked;

    // 생성자, 게터, 세터 등을 포함한 다른 필드들도 추가 가능
}
