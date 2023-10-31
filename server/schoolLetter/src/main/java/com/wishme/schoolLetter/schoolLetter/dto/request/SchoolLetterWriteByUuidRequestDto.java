package com.wishme.schoolLetter.schoolLetter.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchoolLetterWriteByUuidRequestDto {

    private String uuid;
    private Long assetSeq;
    private String content;
    private String nickname;


    @Builder
    public SchoolLetterWriteByUuidRequestDto(String uuid, Long assetSeq, String content, String nickname) {
        this.uuid = uuid;
        this.assetSeq = assetSeq;
        this.content = content;
        this.nickname = nickname;
    }
}
