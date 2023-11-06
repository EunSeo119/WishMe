package com.wishme.myLetter.myLetter.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveReplyRequestDto {

    @NotNull(message = "myLetterSeq은 빈값이 올 수 없습니다")
    private Long myLetterSeq;

//    @NotNull(message = "toUserSeq은 빈값이 올 수 없습니다")
//    private Long toUserSeq;

    @NotBlank(message = "content은 빈값이 올 수 없습니다")
    private String content;

//    @NotBlank(message = "fromUserNickname은 빈값이 올 수 없습니다")
//    private String fromUserNickname;

    @NotNull(message = "color은 빈값이 올 수 없습니다")
    private char color;

    @NotNull(message = "canReply는 빈값이 올 수 없습니다")
    private Boolean canReply;
}
