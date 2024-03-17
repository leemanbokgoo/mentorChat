package com.example.metoChat.domain.metoring;

import com.example.metoChat.web.dto.mentorTime.MentorTimeListResponse;
import com.example.metoChat.web.dto.mentoring.MentoringListResponse;
import com.example.metoChat.web.dto.mentoring.MentoringSaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface MentoringRepositoryCustom {

    // 내 멘토링 조회 // 상태별로 조회
    Page<MentoringListResponse> getMyMentoring(Pageable pageable,Long userId, int state);
    List<MentorTimeListResponse> getTimeByMentoringDate(Long id, LocalDate mentoringDate);
}
