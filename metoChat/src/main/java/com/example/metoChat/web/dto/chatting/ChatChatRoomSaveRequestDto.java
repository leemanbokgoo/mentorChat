package com.example.metoChat.web.dto.chatting;

import com.example.metoChat.domain.chat.ChatChatRoom;
import com.example.metoChat.domain.chat.ChatRoom;
import com.example.metoChat.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatChatRoomSaveRequestDto {
    private User user;
    private ChatRoom chatRoom;


    @Builder
    public ChatChatRoomSaveRequestDto(User user, ChatRoom chatRoom){
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public ChatChatRoom toEntity(){
        return ChatChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }

    public static ChatChatRoomSaveRequestDto from(User user, ChatRoom chatRoom){
        return ChatChatRoomSaveRequestDto.builder()
                .user(user)
                .chatRoom(chatRoom).build();

    }
}
