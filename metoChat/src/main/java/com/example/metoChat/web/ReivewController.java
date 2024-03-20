package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.domain.Reviews.Reviews;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.metoring.Mentoring;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.servcie.MentorService;
import com.example.metoChat.servcie.MentoringService;
import com.example.metoChat.servcie.ReviewsService;
import com.example.metoChat.servcie.UserService;
import com.example.metoChat.web.dto.HttpResponseDto;
import com.example.metoChat.web.dto.Review.SaveReivewRqeustDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/review")
public class ReivewController {

    private final ReviewsService reviewsService;
    private final UserService userService;
    private final MentoringService mentoringService;

    // 사용자 등록
    @PostMapping("/")
    public ResponseEntity save(@RequestBody SaveReivewRqeustDto reivewRqeustDto, @LoginUser SessionUser user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        User findUser = userService.getUserByEmail(user.getEmail());
        Mentoring mentoring = mentoringService.getMentoring(reivewRqeustDto.getMemtoringId());
        Long reviewId =  reviewsService.saveReview(reivewRqeustDto, findUser, mentoring);

        return new ResponseEntity<>(new HttpResponseDto(true, reviewId), httpHeaders, HttpStatus.OK);
    }

    // 사용자 수정
    @PutMapping("/{reviewID}")
    public ResponseEntity stateUpdate(@RequestBody SaveReivewRqeustDto reivewRqeustDto, @PathVariable("reviewID") Long reviewID) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Long reviewId = reviewsService.updateReview(reivewRqeustDto, reviewID);
        return new ResponseEntity<>(new HttpResponseDto(true, reviewId), httpHeaders, HttpStatus.OK);

    }


}
