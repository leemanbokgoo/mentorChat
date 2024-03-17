package com.example.metoChat.web.dto.mento;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
public class MentorListPageDto {

    private int totalNum;
    private int totalPages;
    private int pageNum;
    private int startPage;
    private int endPage;
    private List<MentorListResponseDto> list;

    @Builder
    public MentorListPageDto( int totalNum, int totalPages, int pageNum, int startPage, int endPage, List<MentorListResponseDto> list){
        this.totalNum = totalNum;
        this.pageNum = pageNum;
        this.list = list;
        this.startPage = startPage;
        this.endPage = endPage;
    }
}
