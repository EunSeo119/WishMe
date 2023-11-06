package com.wishme.myLetter.myLetter.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyReplyResponseDto {

    private Long replySeq;

    private String fromUserNickname;

    private char color;

    @Builder
    public MyReplyResponseDto(Long replySeq, String fromUserNickname, char color) {
        this.replySeq = replySeq;
        this.fromUserNickname = fromUserNickname;
        this.color = color;
    }
}
