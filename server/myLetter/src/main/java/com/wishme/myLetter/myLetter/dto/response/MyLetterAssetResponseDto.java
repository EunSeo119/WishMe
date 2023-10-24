package com.wishme.myLetter.myLetter.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyLetterAssetResponseDto {

    private Long assetSeq;

    private String assetImg;

    @Builder
    public MyLetterAssetResponseDto(Long assetSeq, String assetImg) {
        this.assetSeq = assetSeq;
        this.assetImg = assetImg;
    }
}
