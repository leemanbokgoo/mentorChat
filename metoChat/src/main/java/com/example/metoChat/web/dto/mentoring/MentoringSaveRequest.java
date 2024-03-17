package com.example.metoChat.web.dto.mentoring;

import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.metoring.Mentoring;
import com.example.metoChat.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class MentoringSaveRequest {
    private int state;
    private String content;
    private LocalTime starTime;
    private LocalTime endTime;
    private LocalDate mentoringDate;

    @Builder
    public MentoringSaveRequest( String content, LocalTime startTime, LocalTime endTime, LocalDate mentoringDate){
        this.content = content;
        this.starTime = startTime;
        this.endTime = endTime;
        this.state = 2;
        this.mentoringDate = mentoringDate;
    }
    public Mentoring toEntity(User user, Mentor mentor){
        System.out.println(mentor.getId());
        System.out.println("=======================");
        return Mentoring.builder()
                .content(this.content)
                .endTime(this.endTime)
                .startTime(this.starTime)
                .state(2)
                .mentoringDate(this.mentoringDate)
                .user(user)
                .mentor(mentor)
                .build();
    }
}
