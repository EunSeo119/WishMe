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

    private String fromUserNickname;

    private String assetImg;

    private Long assetSeq;

    private Boolean isPublic;

    private Boolean isBad;

    @Builder
    public MyLetterResponseDto(Long myLetterSeq, String fromUserNickname, String assetImg, Boolean isPublic, Boolean isBad, Long assetSeq) {
        this.myLetterSeq = myLetterSeq;
        this.fromUserNickname = fromUserNickname;
        this.assetImg = assetImg;
        this.isPublic = isPublic;
        this.isBad = isBad;
        this.assetSeq = assetSeq;
    }
}
