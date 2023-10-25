package com.wishme.myLetter.myLetter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WriteDeveloperLetterRequestDto {

    // 에셋 일련번호
    private Long assetSeq;
    // 내용
    private String content;
    // 닉네임
    private String nickname;
    // 보내는 사람
    private Long fromUser;
    // 공개여부
    private boolean isPublic;

}
