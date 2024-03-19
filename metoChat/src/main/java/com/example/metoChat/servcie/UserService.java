package com.example.metoChat.servcie;

import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.metoChat.exception.CustomException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.metoChat.exception.ErrorCode.USER_NOT_FOUND;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Transactional(readOnly = true)
    public User getUserByEmail(String email){
        // 사용자 엔티티 조회
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
