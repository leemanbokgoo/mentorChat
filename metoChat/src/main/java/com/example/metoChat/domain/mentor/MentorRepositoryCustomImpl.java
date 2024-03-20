package com.example.metoChat.domain.mentor;

import com.example.metoChat.web.dto.mento.MentorListResponseDto;
import com.example.metoChat.web.dto.mento.SearchRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.metoChat.domain.mentor.QMentor.mentor;
import static com.example.metoChat.domain.user.QUser.user;

@Repository
@RequiredArgsConstructor
public class MentorRepositoryCustomImpl implements MentorRepositoryCustom {
    private final JPAQueryFactory query;


    // 검색 결과 및 토탈 카운트 반환
    @Override
    public Page<MentorListResponseDto> getSearchMentor(Pageable pageable, SearchRequestDto searchRequestDto) {
        List<MentorListResponseDto> content = getFilterSearch( pageable, searchRequestDto);
        Long count = getCount(pageable, searchRequestDto);

        return new PageImpl<>(content, pageable, count);
    }

    // 검색 결과
    @Override
    public List<MentorListResponseDto> getFilterSearch(Pageable pageable, SearchRequestDto searchRequestDto) {
        BooleanBuilder builder = getFilter(searchRequestDto);
        List<MentorListResponseDto> mentorList = query
                .select(
                        Projections.constructor(
                                MentorListResponseDto.class,
                                mentor.id,
                                mentor.occupation,
                                mentor.title,
                                mentor.job,
                                mentor.career,
                                user.name
                        )
                )
                .from(mentor)
                .leftJoin(mentor.user, user)
                .where(
                    builder
                )
                .orderBy(mentor.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return mentorList;
    }

    // 총 페이지 수
    @Override
    public Long getCount(Pageable pageable, SearchRequestDto searchRequestDto) {

        BooleanBuilder builder = getFilter(searchRequestDto);

        Long count = query
                .select(mentor.count())
                .from(mentor)
                .leftJoin(mentor.user, user)
                .where(builder)
                .fetchOne();
        return count;

    }

    public BooleanBuilder getFilter(SearchRequestDto dto){

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(mentor.state.eq(dto.isState())); // 'state'가 1인 경우만 선택
        BooleanBuilder occupationBuilder = new BooleanBuilder();

        if (dto.getOccupation() != null && dto.getOccupation().length() != 0) {
            String[] occupationArr = dto.getOccupation().split(",");
            for (String occupation : occupationArr) {
                System.out.println(occupation);
                occupationBuilder.or(mentor.occupation.eq(Integer.parseInt(occupation)));
            }

        }

        if (dto.getSearch() != null) {
            String search = "%" + dto.getSearch() + "%"; // 검색어를 '%'로 감싸야 합니다.
            occupationBuilder
                    .or(mentor.user.name.like(search))
                    .or(mentor.job.like(search))
                    .or(mentor.title.like(search));
            builder.and(occupationBuilder);
        }

        return builder;
    }
}
