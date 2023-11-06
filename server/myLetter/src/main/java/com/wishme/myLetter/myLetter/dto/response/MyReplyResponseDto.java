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

    private String assetImg;

    @Builder
    public MyReplyResponseDto(Long replySeq, String fromUserNickname, String assetImg) {
        this.replySeq = replySeq;
        this.fromUserNickname = fromUserNickname;
        this.assetImg = assetImg;
    }
}
