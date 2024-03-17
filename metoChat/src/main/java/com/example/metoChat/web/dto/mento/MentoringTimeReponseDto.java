package com.example.metoChat.web.dto.mento;

import com.example.metoChat.domain.DayOfWeek;
import com.example.metoChat.domain.mentorTime.MentorTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class MentoringTimeReponseDto {
    private Long id;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;


    @Builder
    public MentoringTimeReponseDto(MentorTime entitiy) {
        String KDay = entitiy.getDayOfWeek().getKoreanName();

        this.id = entitiy.getId();
        this.dayOfWeek = KDay;
        this.startTime = entitiy.getStartTime();
        this.endTime = entitiy.getEndTime();
    }
}