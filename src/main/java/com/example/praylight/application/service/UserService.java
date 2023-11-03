package com.example.praylight.application.service;

import com.example.praylight.application.dto.PrayerDto;
import com.example.praylight.application.dto.UserDto;
import com.example.praylight.domain.entity.Prayer;
import com.example.praylight.domain.entity.User;
import com.example.praylight.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public Long addUser(UserDto dto){
        User newUser = userRepository.save(User.from(dto));
        return newUser.getId();
    }

//    @Transactional(readOnly = true)
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

}
