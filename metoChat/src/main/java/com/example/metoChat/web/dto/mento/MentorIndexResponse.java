package com.example.metoChat.web.dto.mento;

import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.mentor.Occupation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorIndexResponse {
    private Long id;
    private String occupation;
    private String title;
    private String job;
    private int career;
    private String name;
    private String introduction;
    private int price;
    private int mentoringTime;

    @Builder
    @QueryProjection
    public MentorIndexResponse (Long id, int occupation,String title, String job, int career, String name, String introduction, int price, int mentoringTime){
        String occupationByValue = String.valueOf(Occupation.getOccupationByValue(occupation));

        this.id = id;
        this.occupation = occupationByValue;
        this.title = title;
        this.job =  job;
        this.career = career;
        this.name = name;
        this.price = price;
        this.introduction = introduction;
        this.mentoringTime = mentoringTime;
    }
    public static MentorIndexResponse from(Mentor entity) {
        return MentorIndexResponse.builder()
                .id(entity.getId())
                .occupation(entity.getOccupation())
                .title(entity.getTitle())
                .career(entity.getCareer())
                .job(entity.getJob())
                .name(entity.getUser().getName())
                .price(entity.getPrice())
                .introduction(entity.getIntroduction())
                .mentoringTime(entity.getMentoringTime())
                .build();
    }
}

