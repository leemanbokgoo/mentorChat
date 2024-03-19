package com.example.metoChat.domain.chat;

import com.example.metoChat.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "chatChatRoom")
@Entity
public class ChatChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User <- chatChatRoom
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Room <- chatChatRoom
    @ManyToOne
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    @Builder
    public ChatChatRoom(User user, ChatRoom chatRoom){
        this.user = user;
        this.chatRoom = chatRoom;
    }
}
