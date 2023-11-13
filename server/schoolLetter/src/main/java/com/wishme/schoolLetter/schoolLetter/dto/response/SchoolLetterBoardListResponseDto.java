package com.wishme.schoolLetter.schoolLetter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchoolLetterBoardListResponseDto {

    private Long schoolLetterSeq;
    private Long assetSeq;
    private String assetImg;

    @Builder
    public SchoolLetterBoardListResponseDto(Long schoolLetterSeq, Long assetSeq, String assetImg) {
        this.schoolLetterSeq = schoolLetterSeq;
        this.assetSeq = assetSeq;
        this.assetImg = assetImg;
    }
}
