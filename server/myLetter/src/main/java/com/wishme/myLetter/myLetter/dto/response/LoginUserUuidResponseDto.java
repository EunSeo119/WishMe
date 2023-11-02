package com.wishme.myLetter.myLetter.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginUserUuidResponseDto {

    private String loginUserUuid;

    @Builder
    public LoginUserUuidResponseDto(String loginUserUuid) {
        this.loginUserUuid = loginUserUuid;
    }
}
