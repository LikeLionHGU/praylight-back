package com.example.praylight.presentation.controller;

import com.example.praylight.application.service.MemberService;
import com.example.praylight.application.dto.MemberDto;
import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


import com.example.praylight.application.service.MemberService;
import com.example.praylight.application.dto.MemberDto;
import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
public class MemberController {
    HttpTransport transport;

    JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    private final MemberService memberService;
    @Value("${google.client-id}")
    private String CLIENT_ID;
    @Autowired
    public MemberController(MemberService memberService, @Value("${google.client-id}") String CLIENT_ID) {
        this.memberService = memberService;
        this.CLIENT_ID = CLIENT_ID;
        try {
            transport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            // 적절한 예외 처리를 수행하세요.
            e.printStackTrace();
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<String> save(@RequestBody MemberDto request, @RequestHeader("Authorization") String idTokenString) {
        if (idTokenString.startsWith("Bearer ")) {
            idTokenString = idTokenString.substring("Bearer ".length());
        }
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token verification failed: " + e.getMessage());
        }

        if (idToken != null) {
            // Google ID token is valid
            GoogleIdToken.Payload payload = idToken.getPayload();
            // Extract user information from the payload
            // Use or store profile information
            Long savedId = memberService.addMember(request);
            // id 값을 String으로 변환하여 반환합니다.
            return ResponseEntity.ok(String.valueOf(savedId));
        } else {
            // Invalid Google ID token, reject the membership request
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID token. The token may be malformed or expired.");
        }
    }


    @PostMapping("/signIn")
    public ResponseEntity<String> login(@RequestBody MemberDto request, @RequestHeader("Authorization") String idTokenString) {
        if (idTokenString.startsWith("Bearer ")) {
            idTokenString = idTokenString.substring("Bearer ".length());
        }
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        if (idToken != null) {
            // 로그인 과정을 수행합니다. 데이터베이스에서 사용자를 찾습니다.
            // Get uid from request instead of payload
            String uid = request.getUid();
            MemberDto member = memberService.findMemberByUid(uid);
            if (member != null) {
                // 사용자가 이미 존재하면 로그인을 진행하고, 사용자의 uid를 반환합니다.
                return ResponseEntity.ok(member.getId().toString());
            } else {
                // 사용자가 존재하지 않으면 회원가입 과정을 수행합니다.
                MemberDto newMember = new MemberDto();
                newMember.setUid(uid);
                newMember.setEmail(request.getEmail());
                newMember.setName(request.getName());

                // 회원가입 요청을 수행하는 "/signup" 엔드포인트의 메소드를 호출합니다.
                ResponseEntity<String> response = save(newMember, idTokenString);

                // 회원가입이 성공하면 uid를 반환하고, 실패하면 에러 메시지를 반환합니다.
                if (response.getStatusCode() == HttpStatus.OK) {

                    return ResponseEntity.ok(response.getBody());
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.getBody());
                }
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }



}
