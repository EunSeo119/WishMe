package com.wishme.myLetter.myLetter.dto.response;

import com.wishme.myLetter.asset.domain.Asset;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyLetterResponseDto {

    private Long myLetterSeq;

    private String nickname;

    private Long assetSeq;

    private Boolean isPublic;

    @Builder
    public MyLetterResponseDto(Long myLetterSeq, String nickname, Long assetSeq, Boolean isPublic) {
        this.myLetterSeq = myLetterSeq;
        this.nickname = nickname;
        this.assetSeq = assetSeq;
        this.isPublic = isPublic;
    }
}
