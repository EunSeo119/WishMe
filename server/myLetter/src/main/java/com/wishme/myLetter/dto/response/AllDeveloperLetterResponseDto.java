package com.wishme.myLetter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AllDeveloperLetterResponseDto {

    // 편지 일련번호
    private Long myLetterSeq;
    // 에셋 일련번호
    private Long assetSeq;
    // 닉네임
    private String nickname;


}
