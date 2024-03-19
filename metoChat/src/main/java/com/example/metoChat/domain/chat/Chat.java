package com.example.metoChat.domain.chat;

import com.example.metoChat.domain.BaseTimeEntity;
import com.example.metoChat.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Table(name = "chat")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Chat extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    @Builder
    public Chat(String message, User user, ChatRoom chatRoom){
        this.message = message;
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
