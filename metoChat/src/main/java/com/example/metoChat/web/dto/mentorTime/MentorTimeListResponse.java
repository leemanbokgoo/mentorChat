package com.example.metoChat.web.dto.mentorTime;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class MentorTimeListResponse {

    private Long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private int mentoringTime;


    public MentorTimeListResponse(){}
    public MentorTimeListResponse( Long id, LocalTime startTime,LocalTime endTime, int mentoringTime){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mentoringTime = mentoringTime;
    }

}
