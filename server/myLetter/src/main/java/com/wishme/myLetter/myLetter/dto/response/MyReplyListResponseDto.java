package com.wishme.myLetter.myLetter.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyReplyListResponseDto {

    private long totalReplyCount;

    private long toUserSeq;

    private String toUserNickname;

    private List<MyReplyResponseDto> myReplyResponseDtos;

    @Builder
    public MyReplyListResponseDto(long totalReplyCount, long toUserSeq, String toUserNickname, List<MyReplyResponseDto> myReplyResponseDtos) {
        this.totalReplyCount = totalReplyCount;
        this.toUserSeq = toUserSeq;
        this.toUserNickname = toUserNickname;
        this.myReplyResponseDtos = myReplyResponseDtos;
    }
}
