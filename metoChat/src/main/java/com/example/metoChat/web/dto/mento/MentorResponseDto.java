package com.example.metoChat.web.dto.mento;

import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.mentor.Occupation;
import com.example.metoChat.web.dto.DataResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorResponseDto extends DataResponseDto {

    private Long id;
    private String title;
    private String occupation;
    private String job;
    private int career;
    private int price;
    private int mentoringTime;
    private boolean state;
    private String introduction;
    private String notification;


    @Builder
    public MentorResponseDto(Long id, String title, int occupation,String job,int carrer, int price, int mentoringTime, boolean state, String introduction, String notification) {

        String occupationByValue = String.valueOf(Occupation.getOccupationByValue(occupation));

        this.id = id;
        this.title = title;
        this.occupation = occupationByValue;
        this.job = job;
        this.career = carrer;
        this.price = price;
        this.mentoringTime = mentoringTime;
        this.state = state;
        this.introduction = introduction;
        this.notification = notification;
    }


    public static MentorResponseDto from(Mentor entity) {
        return MentorResponseDto.builder()
                .id(entity.getId())
                .occupation( entity.getOccupation())
                .carrer(entity.getCareer())
                .job(entity.getJob())
                .state(entity.isState())
                .price(entity.getPrice())
                .mentoringTime(entity.getMentoringTime())
                .introduction(entity.getIntroduction())
                .notification(entity.getNotification())
                .build();
    }
}
