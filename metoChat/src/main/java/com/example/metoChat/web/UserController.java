package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import com.example.metoChat.servcie.UserService;
import com.example.metoChat.web.dto.HttpResponseDto;
import com.example.metoChat.web.dto.user.UserUpdateReuqestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private boolean SUCCESS = true;
    private String FAIL = "fail";

    // 프로파일 업데이트
    @PutMapping("/mypage")
    public HttpResponseDto update(@RequestBody UserUpdateReuqestDto requestDto, @LoginUser SessionUser user) {

        if (user != null) {
            User entity = userService.getUserByEmail(user.getEmail());

            entity.update(requestDto.getName());

            if ( entity != null) {
                return HttpResponseDto.builder()
                        .result(SUCCESS)
                        .data(SUCCESS)
                        .build();
            } else {
                return HttpResponseDto.builder()
                        .result(SUCCESS)
                        .data(FAIL)
                        .build();
            }

        } else {
            return HttpResponseDto.builder()
                    .result(SUCCESS)
                    .data(FAIL)
                    .build();
        }
    }

}

