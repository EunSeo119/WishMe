package com.wishme.myLetter.myLetter.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyLetterListResponseDto {

    private boolean isMine;

    private long totalLetterCount;

    private long toUserSeq;

    private String toUserNickname;

    private List<MyLetterResponseDto> myLetterResponseDtoList;

    @Builder
    public MyLetterListResponseDto(boolean isMine, long totalLetterCount, long toUserSeq, String toUserNickname, List<MyLetterResponseDto> myLetterResponseDtoList) {
        this.isMine = isMine;
        this.totalLetterCount = totalLetterCount;
        this.toUserSeq = toUserSeq;
        this.toUserNickname = toUserNickname;
        this.myLetterResponseDtoList = myLetterResponseDtoList;
    }
}
