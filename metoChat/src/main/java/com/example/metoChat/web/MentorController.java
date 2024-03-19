package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import com.example.metoChat.servcie.MentorService;
import com.example.metoChat.servcie.UserService;
import com.example.metoChat.web.dto.HttpResponseDto;
import com.example.metoChat.web.dto.mento.MentorResponseDto;
import com.example.metoChat.web.dto.mento.MentorSaveRequestDto;
import com.example.metoChat.web.dto.mento.MentorUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mentor")
public class MentorController {

    private final MentorService mentorService;
    private final UserService userService;

    // 멘토 등록
    @PostMapping("/")
    public ResponseEntity save(@RequestBody MentorSaveRequestDto requestDto, @LoginUser SessionUser user) {

        HttpHeaders httpHeaders = new HttpHeaders();

        User userEntity = userService.getUserByEmail(user.getEmail());
        Long mentorID = mentorService.save(requestDto, userEntity);

        return new ResponseEntity<>(new HttpResponseDto(true, mentorID), httpHeaders, HttpStatus.OK);
    }

    // 멘토 수정
    @PutMapping("/")
    public ResponseEntity update(@RequestBody MentorUpdateRequestDto requestDto, @LoginUser SessionUser user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        User userEntity = userService.getUserByEmail(user.getEmail());
        Long mentorID = mentorService.update(userEntity.getMentor(), requestDto);
        return new ResponseEntity<>(new HttpResponseDto(true, mentorID), httpHeaders, HttpStatus.OK);
    }


    // 멘토 존재 여부 조회
    @GetMapping("/check/{id}")
    public ResponseEntity mentorChceck(@PathVariable Long id, @LoginUser SessionUser user) {
        // false => 비멘토
        boolean state = false;
        MentorResponseDto responseDto= mentorService.findByIdAndState(id, state);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>( new HttpResponseDto(true, responseDto), httpHeaders, HttpStatus.OK);
    }

    // 멘토링 on 업데이트
    @PutMapping("/state/{state}")
    public ResponseEntity mentorState(@PathVariable boolean state, @LoginUser SessionUser user) {
        boolean mentor= mentorService.stateUpdate(state, user.getEmail());
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>( new HttpResponseDto(true, mentor), httpHeaders, HttpStatus.OK);
    }

}
