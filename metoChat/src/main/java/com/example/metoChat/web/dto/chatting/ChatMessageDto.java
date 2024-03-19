package com.example.metoChat.web.dto.chatting;

import com.example.metoChat.domain.chat.Chat;
import com.example.metoChat.domain.chat.ChatRoom;
import com.example.metoChat.domain.user.User;
import lombok.*;

@Getter
@NoArgsConstructor
public class ChatMessageDto {

    private ChatRoom chatRoom; // 방 번호
    private User user; // 채팅을 보낸 사람
    private String message; // 메시지

    @Builder
    public ChatMessageDto(String message, User user, ChatRoom chatRoom) {
        this.message = message;
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public Chat toEntity(){
        return Chat.builder()
                .message(this.message)
                .user(this.user)
                .chatRoom(this.chatRoom)
                .build();
    }

    public static ChatMessageDto from(String message, User user, ChatRoom chatRoom){
        return ChatMessageDto.builder()
                .message(message)
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }
}
