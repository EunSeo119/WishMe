package com.wishme.schoolLetter.schoolLetter.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchoolLetterWriteRequestDto {

    private Integer schoolSeq;
    private Long assetSeq;
    private String content;
    private String nickname;

    @Builder
    public SchoolLetterWriteRequestDto(Integer schoolSeq, Long assetSeq, String content, String nickname) {
        this.schoolSeq = schoolSeq;
        this.assetSeq = assetSeq;
        this.content = content;
        this.nickname = nickname;
    }

}
