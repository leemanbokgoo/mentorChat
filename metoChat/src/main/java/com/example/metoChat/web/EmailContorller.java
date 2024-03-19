package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.utils.MailUtil;
import com.example.metoChat.web.dto.HttpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/email")
public class EmailContorller {

    private final MailUtil mailUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 멘토 이메일 인증
    @GetMapping("/")
    public ResponseEntity emailValidation(@LoginUser SessionUser user) {
        // 이메일 네자리 암호화해서 보내기
        HttpHeaders httpHeaders = new HttpHeaders();

        String randomNum = mailUtil.sendMail(user.getEmail());
        String hashedPassword = bCryptPasswordEncoder.encode(randomNum);
        return new ResponseEntity<>(new HttpResponseDto(true, hashedPassword), httpHeaders, HttpStatus.OK);

    }

    @GetMapping("/matches")
    public ResponseEntity emailValidation(@RequestParam String sendRandomNum, @RequestParam String userNum, @LoginUser SessionUser user) {

        boolean matches = bCryptPasswordEncoder.matches(userNum, sendRandomNum);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(new HttpResponseDto(true, matches), httpHeaders, HttpStatus.OK);

    }

}
