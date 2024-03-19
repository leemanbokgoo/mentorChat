package com.example.metoChat.web.dto.Review;

import com.example.metoChat.domain.Reviews.Reviews;
import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveReivewRqeustDto {
    private String content;
    private int start;
    private Long mentorId;

    @Builder
    public SaveReivewRqeustDto(String content, int star, Long mentorId){
        this.content = content;
        this.start = star;
        this.mentorId = mentorId;
    }

    public Reviews toEntity(User user, Mentor mentor){
        return Reviews.builder()
                .content(content)
                .star(start)
                .user(user)
                .menetor(mentor)
                .build();
    }
}
