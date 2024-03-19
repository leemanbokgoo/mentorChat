package com.example.metoChat.domain.mentor;

import com.example.metoChat.web.dto.mento.MentorListResponseDto;
import com.example.metoChat.web.dto.mento.SearchRequestDto;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MentorRepositoryCustom {

    Page<MentorListResponseDto> getSearchMentor(Pageable pageable, SearchRequestDto searchRequestDto);
    List<MentorListResponseDto> getFilterSearch(Pageable pageable, SearchRequestDto searchRequestDto );
    Long getCount(Pageable pageable, SearchRequestDto searchRequestDto);
}
