package com.example.metoChat.web;

import com.example.metoChat.domain.DayOfWeek;
import com.example.metoChat.servcie.MentorTimeService;
import com.example.metoChat.utils.DayOfWeekUtil;
import com.example.metoChat.web.dto.HttpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/mentorTime")
public class MentorTimeController {
    private final MentorTimeService mentorTimeService;
    private boolean SUCCESS = true;

    //  멘토 시간/ 아이디 체크
    @GetMapping("/{mentorId}")
    public HttpResponseDto mentorChceck(@PathVariable Long mentorId) {
        return HttpResponseDto.builder()
                .result(SUCCESS)
                .data(mentorTimeService.mentoringTimeCheck(mentorId))
                .build();
    }

    @GetMapping("/day/{dayOfweek}/{id}/{mentoringDate}")
    public HttpResponseDto getMentorTime(@PathVariable("dayOfweek") int dayOfWeek,@PathVariable("id") Long id,@PathVariable("mentoringDate") LocalDate mentoringDate) {


        return HttpResponseDto.builder()
                .result(SUCCESS)
                .data(mentorTimeService.getMentorTime( id, dayOfWeek, mentoringDate))
                .build();
    }
}
