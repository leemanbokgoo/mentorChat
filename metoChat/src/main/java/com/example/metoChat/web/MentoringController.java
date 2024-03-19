package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.mentor.MentorRepository;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import com.example.metoChat.servcie.MentorService;
import com.example.metoChat.servcie.MentoringService;
import com.example.metoChat.servcie.UserService;
import com.example.metoChat.web.dto.HttpResponseDto;
import com.example.metoChat.web.dto.mentoring.MentoringSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/email")
public class MentoringController {
    private final MentoringService mentoringService;
    private final UserService userService;
    private final MentorService mentorService;

    // 멘토링 등록
    @PostMapping("/{mentorId}")
    public ResponseEntity save(@PathVariable("mentorId") Long mentorId, @RequestBody MentoringSaveRequest requestDto, @LoginUser SessionUser user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        User userEntity = userService.getUserByEmail(user.getEmail());
        Mentor mentor = mentorService.getMentor(mentorId);
        boolean mentorID = mentoringService.saveMentoring(requestDto, userEntity, mentor);

        return new ResponseEntity<>(new HttpResponseDto(true, mentorID), httpHeaders, HttpStatus.OK);

        }

    //멘토링 상태 업데이트
    @PutMapping("/state")
    public ResponseEntity stateUpdate(@RequestParam("mentoringId") Long mentoringId, @RequestParam("state") int state, @LoginUser SessionUser user) {
        HttpHeaders httpHeaders = new HttpHeaders();

        // 사용자 엔티티 조회
       User userEntity = userService.getUserByEmail(user.getEmail());
        // 멘토링 생성
        Long mentorID = mentoringService.stateMentoring(mentoringId,state);

        return new ResponseEntity<>(new HttpResponseDto(true, mentorID), httpHeaders, HttpStatus.OK);
    }
}
