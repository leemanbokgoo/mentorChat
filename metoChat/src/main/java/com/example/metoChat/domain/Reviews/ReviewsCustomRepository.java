package com.example.metoChat.domain.Reviews;

import com.example.metoChat.web.dto.Review.ReviewsListResponseDto;
import com.example.metoChat.web.dto.mentoring.MentoringListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

public interface ReviewsCustomRepository {

    // 리뷰 목록 조회
    Page<ReviewsListResponseDto> getReviewList(Pageable pageable, Long userId, int state);

}
