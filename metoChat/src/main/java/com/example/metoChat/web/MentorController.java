package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import com.example.metoChat.exception.CustomException;
import com.example.metoChat.servcie.MentorService;
import com.example.metoChat.servcie.MentorTimeService;
import com.example.metoChat.servcie.UserService;
import com.example.metoChat.web.dto.HttpResponseDto;
import com.example.metoChat.web.dto.mento.MentorResponseDto;
import com.example.metoChat.web.dto.mento.MentorSaveRequestDto;
import com.example.metoChat.web.dto.mento.MentorUpdateRequestDto;
import com.example.metoChat.web.dto.mentorTime.MentorTimeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

import static com.example.metoChat.exception.ErrorCode.MENTOR_NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mentor")
public class MentorController {

    private final MentorService mentorService;
    private final UserService userService;
    private final MentorTimeService mentorTimeService;

    // 멘토 등록
    @PostMapping("/")
    public ResponseEntity save(@RequestBody MentorSaveRequestDto requestDto, @LoginUser SessionUser user) {

        HttpHeaders httpHeaders = new HttpHeaders();

        User userEntity = userService.getUserByEmail(user.getEmail());
        Mentor mentor = mentorService.save(requestDto, userEntity);
        for( MentorTimeSaveRequestDto dto : mentor.getMentoringTimeList()) {
            mentorTimeService.save(dto, mentor );
        }

        return new ResponseEntity<>(new HttpResponseDto(true, mentor.getId()), httpHeaders, HttpStatus.OK);
    }

    // 멘토 수정
    @PutMapping("/")
    public ResponseEntity update(@RequestBody MentorUpdateRequestDto requestDto, @LoginUser SessionUser user) {
        HttpHeaders httpHeaders = new HttpHeaders();

        // 멘토 설정 수정
        User userEntity = userService.getUserByEmail(user.getEmail());
        Mentor mentor = userEntity.getMentor();
        Long mentorID = mentorService.update(mentor, requestDto);

        // 기존 멘토링 시간 설정 삭제
        if (mentor != null) {
            mentorTimeService.delete(mentor);
        } else {
            throw new CustomException(MENTOR_NOT_FOUND);
        }

        // 새로 멘토링 시간 설정 저장
        List<MentorTimeSaveRequestDto> mentorTImelist = requestDto.getMentoringTimeList();
        for( MentorTimeSaveRequestDto dto : mentorTImelist) {
            mentorTimeService.save(dto, mentor );
        }

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

        User findUser = userService.getUserByEmail(user.getEmail());
        boolean mentor= mentorService.stateUpdate(state, findUser.getMentor());

        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>( new HttpResponseDto(true, mentor), httpHeaders, HttpStatus.OK);
    }

}
