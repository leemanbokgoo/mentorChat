package com.example.metoChat.servcie;

import com.example.metoChat.domain.user.Role;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    void 회원_조회() {


        //given
        String name = "홍길동";
        String email = "test@naver.com";
        String passwrod = "12345679";
        Role role = Role.USER;

        User user = new User(name, email, role , passwrod);

        //when
        userRepository.save(user);

        //then
        User findUser = userService.getUserByEmail(email);
        assertThat(findUser.getName()).isEqualTo(name);
        assertThat(findUser.getEmail()).isEqualTo(email);
        assertThat(findUser.getRole()).isEqualTo(role);

    }
}