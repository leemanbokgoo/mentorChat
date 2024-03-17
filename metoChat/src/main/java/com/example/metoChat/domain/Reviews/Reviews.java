package com.example.metoChat.domain.Reviews;

import com.example.metoChat.domain.BaseTimeEntity;
import com.example.metoChat.domain.user.User;
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Reviews(String content, int star ) {
        this.content = content;
        this.star = star;
    }

    // 3일 이후에는 리뷰 업뎃 불가
    public Reviews update(String content, int star ) {
        this.content = content;
        this.star = star;
        return this;
    }
}
