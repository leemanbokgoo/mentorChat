package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import com.example.metoChat.servcie.MentorService;
import com.example.metoChat.servcie.UserService;
import com.example.metoChat.web.dto.HttpResponseDto;
import com.example.metoChat.web.dto.mento.MentorSaveRequestDto;
import com.example.metoChat.web.dto.mento.MentorUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mentor")
public class MentorController {

    private final MentorService mentorService;
    private final UserService userService;
    private boolean SUCCESS = true;
    private String FAIL = "fail";

    // 멘토 등록
    @PostMapping("/")
    public HttpResponseDto save(@RequestBody MentorSaveRequestDto requestDto, @LoginUser SessionUser user) {

        if (user != null) {
            User userEntity = userService.getUserByEmail(user.getEmail());
            Long mentorID = mentorService.save(requestDto, userEntity);
            return HttpResponseDto.builder()
                    .result(SUCCESS)
                    .data(mentorID)
                    .build();
        } else {
            return HttpResponseDto.builder()
                    .result(SUCCESS)
                    .data(FAIL)
                    .build();
        }
    }

    // 멘토 수정
    @PutMapping("/")
    public HttpResponseDto update(@RequestBody MentorUpdateRequestDto requestDto, @LoginUser SessionUser user) {

        if (user != null) {

            User userEntity = userService.getUserByEmail(user.getEmail());
            if ( userEntity != null) {
                Long mentorID = mentorService.update(userEntity.getMentor(), requestDto);
                return HttpResponseDto.builder()
                        .result(SUCCESS)
                        .data(mentorID)
                        .build();
            } else {
                return HttpResponseDto.builder()
                        .result(SUCCESS)
                        .data(FAIL)
                        .build();
            }

        } else {
            return HttpResponseDto.builder()
                    .result(SUCCESS)
                    .data(FAIL)
                    .build();
        }
    }


    // 멘토 존재 여부 조회
    @GetMapping("/check/{id}")
    public HttpResponseDto mentorChceck(@PathVariable Long id, @LoginUser SessionUser user) {
        // false => 비멘토
        boolean state = false;
        return HttpResponseDto.builder()
                .result(SUCCESS)
                .data(mentorService.findByIdAndState(id, state))
                .build();
    }

    // 멘토링 on 업데이트
    @PutMapping("/state/{state}")
    public HttpResponseDto mentorState(@PathVariable boolean state, @LoginUser SessionUser user) {
        return HttpResponseDto.builder()
                .result(SUCCESS)
                .data( mentorService.stateUpdate(state, user.getEmail())).build();
    }

}
