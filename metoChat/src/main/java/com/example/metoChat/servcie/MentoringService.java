package com.example.metoChat.servcie;

import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.metoring.Mentoring;
import com.example.metoChat.domain.metoring.MentoringRepository;
import com.example.metoChat.domain.metoring.MentoringRepositoryImpl;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.exception.CustomException;
import com.example.metoChat.exception.ErrorCode;
import com.example.metoChat.web.dto.mentoring.MentoringListResponse;
import com.example.metoChat.web.dto.mentoring.MentoringSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MentoringService {
    private final MentoringRepository mentoringRepository;
    private final MentoringRepositoryImpl mentoringRepositoryImpl;

    // 멘토링 신청
    @Transactional
    public boolean saveMentoring(MentoringSaveRequest request, User user, Mentor mentor){

        Mentoring entity = mentoringRepository.save(request.toEntity(user ,mentor));
        if( entity != null ){
            return true;
        } else {
            return false;
        }
    }

    // 멘토링 예약 / 취소
    @Transactional
    public Long stateMentoring(Long mentoringId, int state){
        Optional<Mentoring> entity = Optional.ofNullable(mentoringRepository.findById(mentoringId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENTOR_NOT_FOUND)));

        Mentoring mentoring = entity.get();
        if( mentoring != null ){
            mentoring.stateUpdate(state);
        }

        return mentoringId;
    }

    @Transactional(readOnly = true)
    public Page<MentoringListResponse> getMyMentoring( Pageable pageable, Long userId, int state){

        int page = pageable.getPageNumber() - 1; // page 위치에 있는 값은 0부터 시작
        int pageLimit = 5; // 한페이지에 보여줄 글 개수

        Page<MentoringListResponse> list =  mentoringRepositoryImpl.getMyMentoring(PageRequest.of(page, pageLimit),userId, state);
        return list;
    }


}
