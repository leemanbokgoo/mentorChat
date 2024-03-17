package com.example.metoChat.servcie;

import com.example.metoChat.domain.mentor.MentorRepository;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.mentor.MentorRepositoryImpl;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import com.example.metoChat.web.dto.mento.*;
import com.example.metoChat.web.dto.mentorTime.MentorTimeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MentorService {

    private final MentorRepository mentorRepository;
    private final UserRepository userRepository;
    private final MentorRepositoryImpl mentorRepositoryImpl;
    private final MentorTimeService mentorTimeService;

    // 멘토 등록
    @Transactional
    public Long save(MentorSaveRequestDto requestDto, User user) {

        Mentor mentor = mentorRepository.save(requestDto.toEntity(user));
        List<MentorTimeSaveRequestDto> mentorTImelist = requestDto.getMentoringTimeList();

        for( MentorTimeSaveRequestDto dto : mentorTImelist) {
            mentorTimeService.save(dto, mentor );
        }

        return mentor.getId();
    }

    // 멘토 수정
    @Transactional
    public Long update( Mentor mentor,  MentorUpdateRequestDto requestDto) {
        // 기존 멘토링 시간 설정 삭제
        if (mentor != null) {
            mentorTimeService.delete(mentor);
        }
        // 새로 멘토링 시간 저장
        List<MentorTimeSaveRequestDto> mentorTImelist = requestDto.getMentoringTimeList();
        for( MentorTimeSaveRequestDto dto : mentorTImelist) {
            mentorTimeService.save(dto, mentor );
        }
        mentor.update(requestDto);

        return mentor.getId();
    }

    // 멘토리스트조회
    @Transactional(readOnly = true)
    public Page<MentorListResponseDto> findAllMentor(Pageable pageable, String filter, String search){

        int page = pageable.getPageNumber() - 1; // page 위치에 있는 값은 0부터 시작
        int pageLimit = 5; // 한페이지에 보여줄 글 개수
        Page<MentorListResponseDto> list =  mentorRepositoryImpl.getSearchMentor(PageRequest.of(page, pageLimit), new SearchRequestDto(true, filter, search));

        return list;
    }

    // 멘토 상태 업데이트
    @Transactional
    public boolean stateUpdate(boolean state, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email=" + userEmail));
        Mentor mentor = user.getMentor();
        mentor.stateUpdate(state);

        return mentor.isState();
    }

    // 멘토 설정 조회
    @Transactional(readOnly = true)
    public  Optional<Mentor> findById(Long id) {
        Optional<Mentor> entity = mentorRepository.findById(id);
        return entity;
    }

    // 멘토 state가 true인 단일 멘토 조회
    @Transactional(readOnly = true)
    public MentorResponseDto findByIdAndState(Long id, boolean State) {
        MentorResponseDto mentorResponseDto = mentorRepository.findByIdAndState( id,State)
                .map(MentorResponseDto::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 멘토가 없습니다. id=" + id));

        return mentorResponseDto;
    }

    @Transactional(readOnly = true)
    public List<Mentor> test() {
        List<Mentor> list = mentorRepository.findAll();

        return list;
    }
}
