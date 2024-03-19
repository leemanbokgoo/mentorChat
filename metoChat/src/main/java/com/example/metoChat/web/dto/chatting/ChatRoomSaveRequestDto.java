package com.example.metoChat.web.dto.chatting;

import com.example.metoChat.domain.chat.ChatRoom;
import com.example.metoChat.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatRoomSaveRequestDto{

    private String title;

    @Builder
    public ChatRoomSaveRequestDto(String title){
        this.title = title;
    }

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .title(title)
                .build();
    }


}
