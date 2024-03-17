package com.example.metoChat.domain.mentorTime;

import com.example.metoChat.domain.BaseTimeEntity;
import com.example.metoChat.domain.DayOfWeek;
import com.example.metoChat.domain.mentor.Mentor;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalTime;

@RequiredArgsConstructor
@Getter
@Table(name = "mentorTime")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MentorTime extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @Builder
    public MentorTime(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek, Mentor mentor ) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.mentor = mentor;
    }


}
