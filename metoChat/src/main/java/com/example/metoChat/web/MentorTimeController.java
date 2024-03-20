package com.example.metoChat.web;

import com.example.metoChat.domain.DayOfWeek;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.servcie.MentorTimeService;
import com.example.metoChat.utils.DayOfWeekUtil;
import com.example.metoChat.web.dto.HttpResponseDto;
import com.example.metoChat.web.dto.mentorTime.MentorTimeListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mentorTime")
public class MentorTimeController {
    private final MentorTimeService mentorTimeService;
    private boolean SUCCESS = true;

    //  멘토 시간/ 아이디 체크
    @GetMapping("/{mentorId}")
    public ResponseEntity mentorChceck(@PathVariable Long mentorId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<MentorTimeListResponse> mentorTimeList = mentorTimeService.mentoringTimeCheck(mentorId);

        return new ResponseEntity<>(new HttpResponseDto(true, mentorTimeList), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/day/{dayOfweek}/{id}/{mentoringDate}")
    public ResponseEntity getMentorTime(@PathVariable("dayOfweek") int dayOfWeek,@PathVariable("id") Long id,@PathVariable("mentoringDate") LocalDate mentoringDate) {
        HttpHeaders httpHeaders = new HttpHeaders();
        List<Map<String, String>> mentorTimeList = mentorTimeService.getResevationTime( id, dayOfWeek, mentoringDate);

        return new ResponseEntity<>(new HttpResponseDto(true, mentorTimeList), httpHeaders, HttpStatus.OK);
    }
}
