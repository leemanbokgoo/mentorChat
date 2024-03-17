package com.example.metoChat.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class HttpResponseDto {
    public boolean result;
    public Object data;

    @Builder
    public HttpResponseDto( boolean result, Object data ) {
        this.result = result;
        this.data = data;
    }
}
