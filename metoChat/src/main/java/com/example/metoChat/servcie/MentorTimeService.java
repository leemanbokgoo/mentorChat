package com.example.metoChat.servcie;

import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.mentorTime.MentorTime;
import com.example.metoChat.domain.mentorTime.MentorTimeRepository;
import com.example.metoChat.domain.metoring.MentoringRepositoryCustomImpl;
import com.example.metoChat.exception.CustomException;
import com.example.metoChat.web.dto.mentorTime.MentorTimeListResponse;
import com.example.metoChat.web.dto.mentorTime.MentorTimeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.metoChat.exception.ErrorCode.MENTORING_DELETE_ERROR;
import static com.example.metoChat.exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class MentorTimeService {

    private final MentorTimeRepository mentorTimeRepository;
    private final MentoringRepositoryCustomImpl mentoringRepositoryImpl;

    // 멘토링 설정 시간 등록
    @Transactional
    public boolean save(MentorTimeSaveRequestDto requestDto, Mentor mentor){
        MentorTime entity = mentorTimeRepository.save(requestDto.toEntity(mentor));

        if( entity != null ){
            return true;
        } else {
            throw new CustomException(USER_NOT_FOUND);
        }
    }

    // 멘토링 설정 시간 삭제
    @Transactional
    public boolean delete(Mentor mentor){
        List<MentorTime> mentorTimeList = mentor.getMentorTimes();
        for ( MentorTime mentorTime : mentorTimeList ){
            mentorTimeRepository.deleteById(mentorTime.getId());
            throw new CustomException(MENTORING_DELETE_ERROR);
        }
        mentor.deleteMentorTime();

        return true;
    }

    // 멘토링 신청 시간 확인
    @Transactional(readOnly = true)
    public  List<MentorTimeListResponse> mentoringTimeCheck( Long mentorId){
        return mentorTimeRepository.getDayOfWeekDay(mentorId);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> getResevationTime( Long id, int dayOfWeek, LocalDate mentoringDate ){

        List<MentorTimeListResponse> mentorResponses = mentorTimeRepository.getTimeByDayforWeek(id,dayOfWeek);
        if (mentorResponses.size() != 0 ){

            int timePerSession = mentorTimeRepository.findMentoringTimeById(id);

            // 1회당 시간 넘겨줌
            // 해당 멘토링 날짜의 신청된 멘토링 시간 조회
            List<MentorTimeListResponse> mentoringTimeResponse = mentoringRepositoryImpl.getTimeByMentoringDate(id,mentoringDate );

            // 해당 시간대를 시간별로 쪼갬
            List<Map<String, String>> mentoringTime = getTimeList(mentoringTimeResponse,timePerSession);
            List<Map<String, String>> mentorTime = getTimeList(mentorResponses,timePerSession );

            // set 으로 변환
            Set<Map<String, String>> setMentorTime = new LinkedHashSet<>(mentorTime);
            Set<Map<String, String>> setMentoringTime = new LinkedHashSet<>(mentoringTime);

            // 중복 값 삭제
            setMentorTime.removeAll(setMentoringTime);

            // 중복값 삭제 = 신청되지않은 멘토링만 남았다는 뜻.
            for (Map<String, String> map : setMentorTime) {
                map.put("state", "1");
            }

            // 기존 멘토링 set에 값 추가
            for (Map<String, String> map : setMentoringTime) {
                map.put("state", "0");
            }

            setMentorTime.addAll(setMentoringTime);
            List<Map<String, String>> list = new ArrayList<>(setMentorTime);

            return list;

        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> getTimeList(List<MentorTimeListResponse> responses, int mentoringTime ){

        List<Map<String,String>> mentorTime = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for ( MentorTimeListResponse item : responses) {

            //현재날짜와 startTime을 이용해서 yy-mm-dd hh-mm 형식 맞춤
            LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), item.getStartTime());

            // starTime이 endTim보다 이전인 동안 while
            while (startTime.isBefore(LocalDateTime.of(LocalDate.now(), item.getEndTime()))) {

                LocalDateTime endTime = startTime.plusMinutes(item.getMentoringTime());
                Map<String, String> map = new HashMap<>();
                map.put("startTime", startTime.format(formatter));
                map.put("endTime", endTime.format(formatter));

                // 1회당 시간만큼 더하기 //초기화
                startTime = endTime;
                mentorTime.add(map);
            }

            // 마지막 시간대까지 추가
            Map<String, String> map = new HashMap<>();

            LocalTime endTime = item.getEndTime();
            LocalTime endStatTime = endTime.minusMinutes(mentoringTime);

            map.put("startTime", endStatTime.format(formatter));
            map.put("endTime", endTime.format(formatter));

            mentorTime.add(map);

        }

        return mentorTime;
    }
}
