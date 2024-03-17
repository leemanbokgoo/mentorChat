package com.example.metoChat.web.dto.mentorTime;

import com.example.metoChat.domain.DayOfWeek;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.mentorTime.MentorTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class MentorTimeSaveRequestDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;

    @Builder
    public MentorTimeSaveRequestDto(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
    }

    public MentorTime toEntity(Mentor mentor) {
        return MentorTime.builder()
                .startTime(this.startTime)
                .endTime(this.endTime)
                .dayOfWeek(this.dayOfWeek)
                .mentor(mentor)
                .build();
    }
}
