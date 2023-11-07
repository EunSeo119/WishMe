package com.wishme.myLetter.myLetter.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyDetailResponseDto {

    private Long replySeq;

    private String toUserNickname;

    private String fromUserNickname;

    private String content;

    private Long letterSeq;

    private char color;

    @Builder
    public ReplyDetailResponseDto(Long replySeq, String toUserNickname, String fromUserNickname, String content, Long letterSeq, char color) {
        this.replySeq = replySeq;
        this.toUserNickname = toUserNickname;
        this.fromUserNickname = fromUserNickname;
        this.content = content;
        this.letterSeq = letterSeq;
        this.color = color;
    }
}
