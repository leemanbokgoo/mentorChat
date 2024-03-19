package com.example.metoChat.servcie;

import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.metoChat.exception.CustomException;

import java.util.Optional;

import static com.example.metoChat.exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    public User getUserByEmail(String email){
        // 사용자 엔티티 조회
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.get() != null ){
            throw new CustomException(USER_NOT_FOUND);
        }

        return optionalUser.get();
    }
}
