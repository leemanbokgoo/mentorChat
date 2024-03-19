package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.web.dto.HttpResponseDto;
import com.example.metoChat.web.dto.mento.MentorSaveRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatContoller {

    // 채팅방생성
    @PostMapping("/chat")
    public void createRoom(@RequestParam("name") String name, @LoginUser SessionUser user) {

    }

    // 채팅방 메세지 조회
    @GetMapping("/chat/{chatRoomId}/message")
    public void getMessage(){

    }

    // 채팅방 메세지 전송
    @PostMapping("/chat/{chatRoomId}/message")
    public void sendMessage(){

    }

    // 내 채팅방 목록 조회
    @GetMapping("/chat/rooms/{userId}")
    public void getMyRooms(){

    }
}
