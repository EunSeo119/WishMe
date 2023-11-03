package com.wishme.schoolLetter.asset.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssetResponseDto {

    private Long assetSeq;
    private String assetImg;

    @Builder
    public AssetResponseDto(Long assetSeq, String assetImg) {
        this.assetSeq = assetSeq;
        this.assetImg = assetImg;
    }
}
