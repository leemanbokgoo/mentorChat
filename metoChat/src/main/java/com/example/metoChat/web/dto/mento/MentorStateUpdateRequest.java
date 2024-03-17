package com.example.metoChat.web.dto.mento;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorStateUpdateRequest {

    @NotEmpty
    private Long id;

    @NotEmpty
    private boolean state;
}
