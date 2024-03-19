package com.example.metoChat.web;

import com.example.metoChat.config.auth.LoginUser;
import com.example.metoChat.config.auth.dto.SessionUser;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import com.example.metoChat.servcie.S3ImageService;
import com.example.metoChat.servcie.UserService;
import com.example.metoChat.web.dto.HttpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/S3")
public class S3Contoller {

    private final S3ImageService s3ImageService;
    private final UserService userService;

    @PostMapping("/profileImg/upload")
    public ResponseEntity<?> profileS3Upload(@RequestParam("image") MultipartFile image,  @LoginUser SessionUser user) throws Exception {
        String profileImage = s3ImageService.upload(image);
        HttpHeaders httpHeaders = new HttpHeaders();

        // 올린 이미지 주소 db 저장
        User userEntity = userService.getUserByEmail(user.getEmail());
        userEntity.updateProfileImg(profileImage);

        return new ResponseEntity<>(new HttpResponseDto(true, profileImage), httpHeaders, HttpStatus.OK);

    }

    @GetMapping("/profileImg/delete")
    public ResponseEntity<?> s3delete(@RequestParam String imgAddr,@LoginUser SessionUser user) throws Exception {
        s3ImageService.deleteImageFromS3(imgAddr);
        HttpHeaders httpHeaders = new HttpHeaders();

        // db 삭제
        User userEntity = userService.getUserByEmail(user.getEmail());
        userEntity.updateProfileImg(null);

        return new ResponseEntity<>(new HttpResponseDto(true, userEntity.getId()), httpHeaders, HttpStatus.OK);
    }
}
