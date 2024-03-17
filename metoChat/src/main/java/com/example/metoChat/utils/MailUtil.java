package com.example.metoChat.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class MailUtil {
    private final JavaMailSender javaMailSender;
    private static int randomNumber;

    public String sendMail(String ToEmail) {
        try {

            // 6자리 랜덤 숫자 생성 (100000 이상, 999999 이하)
            Random random = new Random();
            randomNumber = 100000 + random.nextInt(900000);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setSubject("MentorChat에서 보낸 [ 인증 메일 ] 입니다."); // 제목
            helper.setTo(ToEmail); // 받는사람
            helper.setFrom("tmkim@wbsoft.kr"); // 보내는 사람 *application.properties의 값과 동일해야 합니다.

            String BODY = String.join(
                    System.getProperty("line.separator"),
                    "인증 번호는 " + randomNumber + "입니다."
            );
            helper.setText(BODY, true);

            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(randomNumber);
    }

}