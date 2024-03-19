package com.example.metoChat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //400 BAD_REQUEST 잘못된 요청
    INVALID_PARAMETER(400, "파라미터 값을 확인해주세요."),

    //404 NOT_FOUND 잘못된 리소스 접근
    MENTOR_NOT_FOUND(404, "존재하지 않는 멘토 ID 입니다."),
    USER_NOT_FOUND(404, "존재하지 않는 사용자 ID 입니다."),
    MENTORING_NOT_FOUND(404, "존재하지 않는 멘토링 ID 입니다."),
    REIVEW_NOT_FOUND(404, "존재하지 않는 리뷰 ID 입니다."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버에러입니다. 개발자에게 문의해주세요."),
    // MENTORING 관련 SERVER ERROR
    MENTORING_DELETE_ERROR(500, "멘토링타임이 삭제 되지않았습니다.");


    private final int status;
    private final String message;
}