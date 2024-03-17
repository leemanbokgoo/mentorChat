package com.example.metoChat.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // 그래서 코드별 키 값을 ROLE_XXX 등으로 지정
    GUEST   ("ROLE_GUEST", "손님"),
    USER   ("ROLE_USER", "일반 사용자"),
    METEE   ("ROLE_MENTEE", "멘티"),
    mentorR    ("ROLE_mentorR", "멘토");

    private final String key;
    private final String title;
}
