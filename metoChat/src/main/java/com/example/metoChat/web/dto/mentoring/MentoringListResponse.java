package com.example.metoChat.web.dto.mentoring;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class MentoringListResponse {

    private Long id;
    private String name;
    private String title;
    private int state;
    private LocalDate mentoringDate;
    private LocalTime endTime;
    private LocalTime startTime;
    private LocalDateTime createdDate;

    @Builder
    public MentoringListResponse( Long id, String name, String title, int state, LocalDate mentoringDate, LocalTime endTime, LocalTime startTime, LocalDateTime createdDate){
        this.id = id;
        this.name = name;
        this.title = title;
        this.state = state;
        this.mentoringDate = mentoringDate;
        this.endTime = endTime;
        this.startTime = startTime;
        this.createdDate = createdDate;
    }
}
