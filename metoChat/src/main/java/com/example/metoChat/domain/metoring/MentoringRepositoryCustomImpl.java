package com.example.metoChat.domain.metoring;

import com.example.metoChat.web.dto.mentorTime.MentorTimeListResponse;
import com.example.metoChat.web.dto.mentoring.MentoringListResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.example.metoChat.domain.metoring.QMentoring.mentoring;

import java.time.LocalDate;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class MentoringRepositoryCustomImpl implements MentoringRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public Page<MentoringListResponse> getMyMentoring(Pageable pageable, Long userId , int state) {
        List<MentoringListResponse> list = query.select(
                Projections.constructor(
                        MentoringListResponse.class,
                        mentoring.id,
                        mentoring.mentor.user.name,
                        mentoring.mentor.title,
                        mentoring.state,
                        mentoring.mentoringDate,
                        mentoring.endTime,
                        mentoring.startTime,
                        mentoring.createdDate.as("createdDate")
                )
        ).from(mentoring)
                .where( mentoring.user.id.eq(userId), mentoring.state.eq(state))
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(mentoring.id)
                .from(mentoring)
                .where(mentoring.user.id.eq(userId), mentoring.state.eq(state))
                .fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public List<MentorTimeListResponse> getTimeByMentoringDate(Long id, LocalDate mentoringDate) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(mentoring.mentoringDate.eq(mentoringDate));
        builder.and(mentoring.mentor.id.eq(id));
        builder.and(mentoring.state.eq(1 ).or(mentoring.state.eq(2)) );

        System.out.println( mentoringDate + " =-============");
        return query
                .select(
                        Projections.constructor(
                                MentorTimeListResponse.class,
                                mentoring.id,
                                mentoring.startTime,
                                mentoring.endTime,
                                mentoring.mentor.mentoringTime
                        )
                ).from(mentoring)
                .where(builder)
                .fetch();
    }

}
