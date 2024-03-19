package com.example.metoChat.domain.chat;

import com.example.metoChat.domain.Reviews.Reviews;
import com.example.metoChat.domain.chat.Chat;
import com.example.metoChat.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Table(name = "chatRoom")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = true)
    private String title;

    // Room <- chat
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    // Room <- chatChatRoom
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatChatRoom> chatRooms = new ArrayList<>();

    @Builder
    public ChatRoom( String title) {
        this.title = title;
    }


}
