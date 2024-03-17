package com.example.metoChat.domain.mentorTime;

import com.example.metoChat.domain.DayOfWeek;
import com.example.metoChat.utils.DayOfWeekUtil;
import com.example.metoChat.web.dto.mentorTime.MentorTimeListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.metoChat.domain.mentorTime.QMentorTime.mentorTime;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MentorTimeRepositoryImpl implements MentorTimeRepositoryCustom{
    private final JPAQueryFactory query;

    @Override
    public List<MentorTimeListResponse> getDayOfWeekDay(Long mentorId) {
        return query
                .select(Projections.constructor(
                        MentorTimeListResponse.class,
                        mentorTime.dayOfWeek,
                        mentorTime.startTime,
                        mentorTime.endTime
                )
            ).from(mentorTime)
                .where(mentorTime.id.eq(mentorId))
                .fetch();
    }

    @Override
    public List<MentorTimeListResponse> getTimeByDayforWeek(Long id, int dayOfWeek) {
        DayOfWeek day = DayOfWeekUtil.parseDayOfWeek(dayOfWeek);

        List<MentorTimeListResponse> list = query
                .select(Projections.constructor(
                                MentorTimeListResponse.class,
                                mentorTime.id,
                                mentorTime.startTime,
                                mentorTime.endTime,
                                mentorTime.mentor.mentoringTime
                        )
                ).from(mentorTime)
                .where(mentorTime.mentor.id.eq(id), mentorTime.dayOfWeek.eq(day))
                .orderBy(mentorTime.id.desc())
                .fetch();
        return list;
    }


}
