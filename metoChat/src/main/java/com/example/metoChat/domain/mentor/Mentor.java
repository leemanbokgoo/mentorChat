package com.example.metoChat.domain.mentor;

import com.example.metoChat.domain.BaseTimeEntity;
import com.example.metoChat.domain.mentorTime.MentorTime;
import com.example.metoChat.domain.metoring.Mentoring;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.web.dto.mento.MentorUpdateRequestDto;
import com.example.metoChat.web.dto.mentorTime.MentorTimeSaveRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "mentor")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Mentor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(length = 30, nullable = false)
    private String title;

    // 직무
    @Column(length = 20, nullable = false)
    private int occupation;

    // 직업
    @Column(length = 20, nullable = false)
    private String job;

    // 경력
    @Column( nullable = false)
    private int career;

    // 멘토링 여부
    @Column( nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean state;

    // 소개글
    @Column(columnDefinition = "TEXT", nullable = false)
    private String introduction;

    // 환영공지
    @Column(columnDefinition = "TEXT", nullable = false)
    private String notification;

    // 1회당 가격
    @Column( nullable = false)
    private int price;

    // 1회당 시간
    @Column( nullable = false)
    private int mentoringTime;

    // 멘토링 시간 설정을 받아오기위한 필드
    @Transient
    private List<MentorTimeSaveRequestDto> mentoringTimeList;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 실제 저장되는 형식 필드
    // mentor <- mentorTime
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<MentorTime> mentorTimes = new ArrayList<>();

    // 실제 저장되는 형식 필드
    // mentor <- mentorTime
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mentoring> mentoring = new ArrayList<>();

    @Builder
    public Mentor( String title, int occupation, String job, int career, String introduction, String notification, int price, User user, int mentoringTime ) {
        this.title = title;
        this.occupation = occupation;
        this.job = job;
        this.career = career;
        this.introduction = introduction;
        this.notification = notification;
        this.price = price;
        this.mentoringTime = mentoringTime;
        this.user = user;
    }

    public Mentor update( MentorUpdateRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.occupation = requestDto.getOccupation();
        this.job = requestDto.getJob();
        this.career = requestDto.getCareer();
        this.state = requestDto.isState();
        this.notification = requestDto.getNotification();
        this.introduction = requestDto.getIntroduction();
        this.price = requestDto.getPrice();
        this.mentoringTime = requestDto.getMentoringTime();
        return this;
    }

    public Mentor stateUpdate( boolean state ) {
        this.state = state;
        return this;
    }

    public void deleteMentorTime(){
        this.mentorTimes = null;
    }
}
