package com.example.metoChat.servcie;

import com.example.metoChat.domain.Reviews.Reviews;
import com.example.metoChat.domain.Reviews.ReviewsRepository;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import com.example.metoChat.exception.CustomException;
import com.example.metoChat.web.dto.Review.SaveReivewRqeustDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.metoChat.exception.ErrorCode.REIVEW_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class ReviewsService {

    private final ReviewsRepository reviewsRepository;
    private final MentorService mentorService;
    private final UserService userService;

    // 리뷰 등록
    public Long saveReview(SaveReivewRqeustDto reivewRqeustDto, String email){

        User user = userService.getUserByEmail(email);
        Mentor mentor = mentorService.getMentor(reivewRqeustDto.getMentorId());
        Reviews reviews = reviewsRepository.save(reivewRqeustDto.toEntity(user, mentor ));

        return reviews.getId();
    }

    // 리뷰 수정
    public Long updateReview(SaveReivewRqeustDto reivewRqeustDto, Long reveiwId){
        Optional<Reviews> optionalReviews = Optional.ofNullable( reviewsRepository.findById(reveiwId)
                .orElseThrow(() -> new CustomException(REIVEW_NOT_FOUND)));
        optionalReviews.get().update(reivewRqeustDto);
        return optionalReviews.get().getId();
    }

    // 리뷰 목록 조회

    // 내 리뷰 목록 조회

    // 리뷰 조회
}
