package com.example.praylight.presentation.controller;

import com.example.praylight.application.service.UserService;
import com.example.praylight.application.dto.UserDto;
import com.example.praylight.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Long> save(@RequestBody UserDto request) {
        Long savedId = userService.addUser(request);
        return ResponseEntity.ok(savedId);
    }

//    @GetMapping("/users")
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        List<User> users = userService.getAllUsers();
//        List<UserDto> response = users.stream()
//                .map(UserDto::from)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(response);
//    }

//    @PatchMapping("/userRoom/user/delete")
//    public ResponseEntity<Void> deleteById(@RequestParam Long userId) {
//        userService.softDeleteUser(userId);
//        return ResponseEntity.ok(null);
//    }
}
