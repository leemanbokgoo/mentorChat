package com.example.metoChat.servcie;

import com.example.metoChat.domain.Reviews.Reviews;
import com.example.metoChat.domain.Reviews.ReviewsRepository;
import com.example.metoChat.domain.metoring.Mentoring;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.exception.CustomException;
import com.example.metoChat.web.dto.Review.ReviewsListResponseDto;
import com.example.metoChat.web.dto.Review.SaveReivewRqeustDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.metoChat.exception.ErrorCode.REIVEW_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ReviewsService {

    private final ReviewsRepository reviewsRepository;

    // 리뷰 등록
    @Transactional
    public Long saveReview(SaveReivewRqeustDto reivewRqeustDto, User user, Mentoring mentoring){
        Reviews reviews = reviewsRepository.save(reivewRqeustDto.toEntity(user, mentoring ));
        return reviews.getId();
    }

    // 리뷰 수정
    @Transactional
    public Long updateReview(SaveReivewRqeustDto reivewRqeustDto, Long reveiwId){
        Reviews reviews = reviewsRepository.findById(reveiwId)
                .orElseThrow(() -> new CustomException(REIVEW_NOT_FOUND));

        reviews.update(reivewRqeustDto);
        return reviews.getId();
    }

    // 리뷰 목록 조회
    @Transactional(readOnly = true)
    public Page<ReviewsListResponseDto> getReviewsList(Pageable pageable , Long id, int state){
        int page = pageable.getPageNumber() - 1; // page 위치에 있는 값은 0부터 시작
        int pageLimit = 5; // 한페이지에 보여줄 글 개수
        return reviewsRepository.getReviewList(PageRequest.of(page, pageLimit), id, state);
    }
}
