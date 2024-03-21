package com.example.metoChat.domain.mentorTime;

import com.example.metoChat.domain.DayOfWeek;
import com.example.metoChat.web.dto.mentorTime.MentorTimeListResponseDto;

import java.util.List;

public interface MentorTimeRepositoryCustom {
    // 시간 및 요일 조회
    List<MentorTimeListResponseDto> getDayOfWeekDay(Long mentorId);
    List<MentorTimeListResponseDto> getTimeByDayforWeek( Long id, int dayOfWeek);
}
