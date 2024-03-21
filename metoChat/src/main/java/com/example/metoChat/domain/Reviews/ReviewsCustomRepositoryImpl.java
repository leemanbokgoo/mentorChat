package com.example.metoChat.domain.Reviews;

import com.example.metoChat.web.dto.Review.ReviewsListResponseDto;
import com.example.metoChat.web.dto.mento.SearchRequestDto;
import com.example.metoChat.web.dto.mentoring.MentoringListResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.metoChat.domain.Reviews.QReviews.reviews;
import static com.example.metoChat.domain.mentor.QMentor.mentor;
import static com.example.metoChat.domain.metoring.QMentoring.mentoring;

@Repository
@RequiredArgsConstructor
public class ReviewsCustomRepositoryImpl implements ReviewsCustomRepository{
    private final JPAQueryFactory query;

    // 리뷰목록조회
    @Override
    public Page<ReviewsListResponseDto> getReviewList(Pageable pageable, Long id, int state) {

        BooleanBuilder builder = getFilter(state, id);

        List<ReviewsListResponseDto> list = query.select(
                        Projections.constructor(
                                ReviewsListResponseDto.class,
                                reviews.id,
                                reviews.content,
                                reviews.star,
                                reviews.mentoring.mentor.user.name,
                                reviews.createdDate.as("createdDate")
                        )
                ).from(reviews)
                .where( builder)
                .offset(pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = query.select(mentoring.id)
                .from(reviews)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    public BooleanBuilder getFilter(int state, Long id){

        BooleanBuilder builder = new BooleanBuilder();

        // state 0 -> 해당 멘토에게 달린 리뷰 목록 조회
        if ( state == 0) {
            builder.and(reviews.mentoring.mentor.id.eq(id));

        // state 1 -> 내가 쓴 리뷰 목록 조회
        } else if ( state == 1) {
            builder.and(reviews.user.id.eq(id));

        }

        return builder;
    }
}
