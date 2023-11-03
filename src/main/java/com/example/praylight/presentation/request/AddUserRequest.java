package com.example.praylight.presentation.request;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddUserRequest {

    private Long id;

    private String name;

    private String email;
}