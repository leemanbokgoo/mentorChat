package com.example.metoChat.config.auth.dto;

import com.example.metoChat.domain.user.Role;
import com.example.metoChat.domain.user.User;
import lombok.Getter;

import java.io.Serializable;
import java.util.Optional;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private Role role;
    // 나중에 이미지 추가
//    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
