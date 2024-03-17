package com.example.metoChat.web.dto.mento;

import com.example.metoChat.domain.mentor.Mentor;
import com.example.metoChat.domain.mentor.Occupation;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorListResponseDto {
    private Long id;
    private String occupation;
    private String title;
    private String job;
    private int career;
    private String name;

    @Builder
    @QueryProjection
    public MentorListResponseDto (Long id, int occupation,String title, String job, int career, String name ){
        String occupationByValue = String.valueOf(Occupation.getOccupationByValue(occupation));
        System.out.println(occupationByValue);

        this.id = id;
        this.occupation = occupationByValue;
        this.title = title;
        this.job =  job;
        this.career = career;
        this.name = name;
    }
    public static MentorListResponseDto from(Mentor entity) {
        return MentorListResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .occupation(entity.getOccupation())
                .career(entity.getCareer())
                .job(entity.getJob())
                .name(entity.getUser().getName())
                .build();
    }
}
