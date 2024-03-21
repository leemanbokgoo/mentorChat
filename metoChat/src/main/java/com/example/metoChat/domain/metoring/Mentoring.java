package com.example.metoChat.domain.metoring;


import com.example.metoChat.domain.BaseTimeEntity;
import com.example.metoChat.domain.Reviews.Reviews;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "mentoring")
@Entity

// 멘토링 신청 관련
public class Mentoring extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "int DEFAULT 2")
    private int state;

    @Column( nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private LocalDate mentoringDate;

    // ManyToOne 어노테이션을 사용하여 User 엔티티와의 관계를 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // user_id 컬럼을 외래키로 사용하여 조인
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    // mentor <- reviews
    @OneToMany(mappedBy = "mentoring", cascade = CascadeType.ALL)
    private List<Reviews> reviews = new ArrayList<>();

    @Builder
    public Mentoring ( String content, LocalTime startTime , int state, LocalTime endTime, LocalDate mentoringDate, User user , Mentor mentor) {
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mentoringDate = mentoringDate;
        this.state = state;
        this.user = user;
        this.mentor = mentor;
    }


    public Mentoring update ( String content, LocalTime startTime ,LocalTime endTime, LocalDate mentoringDate, int state) {
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mentoringDate = mentoringDate;
        this.state = state;

        return this;
    }

    public Mentoring stateUpdate( int state ){
        this.state = state;
        return this;
    }

}