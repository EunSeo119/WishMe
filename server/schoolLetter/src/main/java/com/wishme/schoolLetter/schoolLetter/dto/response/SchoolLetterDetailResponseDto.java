package com.wishme.schoolLetter.schoolLetter.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class SchoolLetterDetailResponseDto {

    private Long schoolLetterSeq;
    private String schoolName;
    private String content;
    private String nickname;
    private Date createAt;

    @Builder
    public SchoolLetterDetailResponseDto(Long schoolLetterSeq, String schoolName, String content, String nickname, Date createAt) {
        this.schoolLetterSeq = schoolLetterSeq;
        this.schoolName = schoolName;
        this.content = content;
        this.nickname = nickname;
        this.createAt = createAt;
    }
}
