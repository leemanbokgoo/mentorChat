package com.example.metoChat.domain.Reviews;

import com.example.metoChat.domain.BaseTimeEntity;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.metoring.Mentoring;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.web.dto.Review.SaveReivewRqeustDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Table(name = "reivews")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Reviews extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column( nullable = false)
    private int star;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mentoring_id")
    private Mentoring mentoring;

    @Builder
    public Reviews(String content, int star, User user, Mentoring mentoring ) {
        this.content = content;
        this.star = star;
        this.user = user;
        this.mentoring = mentoring;
    }

    // 3일 이후에는 리뷰 업뎃 불가
    public Reviews update(SaveReivewRqeustDto reivewRqeustDto) {
        this.content = reivewRqeustDto.getContent();
        this.star = reivewRqeustDto.getStart();
        return this;
    }
}
