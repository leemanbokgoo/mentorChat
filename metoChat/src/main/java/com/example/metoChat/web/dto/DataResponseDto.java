package com.example.metoChat.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
@NoArgsConstructor
@Getter
public abstract class DataResponseDto {
    public LocalDateTime createdDate;
    public LocalDateTime modifiedDate;
}
