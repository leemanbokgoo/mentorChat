package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.mentor.MentorRepository;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import com.example.metoChat.servcie.MentoringService;
import com.example.metoChat.servcie.UserService;
import com.example.metoChat.web.dto.HttpResponseDto;
import com.example.metoChat.web.dto.mentoring.MentoringSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/email")
public class MentoringController {
    private final MentoringService mentoringService;
    private final UserService userService;
    private final MentorRepository mentorRepository;

    private boolean SUCCESS = true;
    private boolean FAIL = false;

    @PostMapping("/{mentorId}")
    public HttpResponseDto save(@PathVariable("mentorId") Long mentorId, @RequestBody MentoringSaveRequest requestDto, @LoginUser SessionUser user) {

        User userEntity = userService.getUserByEmail(user.getEmail());
        Mentor mentor = userEntity.getMentor();

        if( mentor == null ){
            Optional<Mentor> optionalMentor = mentorRepository.findById(mentorId);
            mentor = optionalMentor.get();
        }

        if (user != null) {
            // 멘토링 생성
            boolean mentorID = mentoringService.saveMentoring(requestDto, userEntity, mentor);

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

    @PutMapping("/state")
    public HttpResponseDto stateUpdate(@RequestParam("mentoringId") Long mentoringId, @RequestParam("state") int state, @LoginUser SessionUser user) {

        // 사용자 엔티티 조회
       User userEntity = userService.getUserByEmail(user.getEmail());

        if ( userEntity != null) {
            // 멘토링 생성
            Long mentorID = mentoringService.stateMentoring(mentoringId,state);

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
}
