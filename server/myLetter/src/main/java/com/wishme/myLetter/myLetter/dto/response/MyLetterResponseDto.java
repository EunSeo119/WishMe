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

    private Long assetSeq;

    private Boolean isPublic;

    @Builder
    public MyLetterResponseDto(Long myLetterSeq, String fromUserNickname, Long assetSeq, Boolean isPublic) {
        this.myLetterSeq = myLetterSeq;
        this.fromUserNickname = fromUserNickname;
        this.assetSeq = assetSeq;
        this.isPublic = isPublic;
    }
}
