package com.wishme.myLetter.myLetter.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMyLetterRequestDto {

    @NotNull(message = "assetSeq은 빈값이 올 수 없습니다")
    private Long assetSeq;

    @NotBlank(message = "nickname은 빈값이 올 수 없습니다")
    private String fromUserNickname;

    @NotBlank(message = "content은 빈값이 올 수 없습니다")
    private String content;

    @NotNull(message = "isPublic은 빈값이 올 수 없습니다")
    private Boolean isPublic;

    @NotBlank(message = "toUserUuid은 빈값이 올 수 없습니다")
    private String toUserUuid;
}
