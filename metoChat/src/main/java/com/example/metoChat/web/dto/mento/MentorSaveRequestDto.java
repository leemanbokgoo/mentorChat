package com.example.metoChat.web.dto.mento;

import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.user.User;
import com.example.metoChat.web.dto.mentorTime.MentorTimeSaveRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class MentorSaveRequestDto {

    @NotBlank(message = "제목는 필수값입니다.")
    private String title;

    @NotBlank(message = "직군는 필수값입니다.")
    private int occupation;

    @NotBlank(message = "직무는 필수값입니다.")
    private String job;

    @PositiveOrZero
    @NotBlank(message = "경력는 필수값입니다.")
    private int career;

    private boolean state;

    @NotBlank(message = "소개 필수값입니다.")
    private String introduction;

    @NotBlank(message = "공지는 필수값입니다.")
    private String notification;

    @NotBlank(message = "가격은 필수값입니다.")
    private int price;

    @NotBlank(message = "1회당 멘토링시간은 필수값입니다.")
    private int mentoringTime;

    @NotBlank(message = "멘토링 시간 설정은 필수값입니다.")
    private List<MentorTimeSaveRequestDto> mentoringTimeList;

    private User user;

    @Builder
    public MentorSaveRequestDto(Mentor entity) {
        this.title = entity.getTitle();
        this.occupation = entity.getOccupation();
        this.job = entity.getJob();
        this.career = entity.getCareer();
        this.introduction = entity.getIntroduction();
        this.notification = entity.getNotification();
        this.price = entity.getPrice();
        this.user = entity.getUser();
        this.mentoringTimeList = entity.getMentoringTimeList();
        this.mentoringTime = entity.getMentoringTime();
    }

    public Mentor toEntity(User user) {
        return Mentor.builder()
                .title(this.title)
                .career(this.career)
                .user(user)
                .job(this.job)
                .occupation(this.occupation)
                .price(this.price)
                .introduction(this.introduction)
                .notification(this.notification)
                .mentoringTime(this.mentoringTime)
                .build();
    }
}
