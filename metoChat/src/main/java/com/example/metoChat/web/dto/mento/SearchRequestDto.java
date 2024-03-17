package com.example.metoChat.web.dto.mento;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SearchRequestDto {

    // 필터
    private boolean state;
    private String occupation;

    // 검색
    private String search;


    @Builder
    public SearchRequestDto( boolean state, String occupation, String search ){
        this.state = state;
        this.occupation = occupation;
        this.search = search;
    }
}
