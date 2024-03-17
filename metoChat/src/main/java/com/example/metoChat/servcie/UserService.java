package com.example.metoChat.servcie;

import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    public User getUserByEmail(String email){
        // 사용자 엔티티 조회
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. user=" + email)));

        return optionalUser.get();
    }
}
