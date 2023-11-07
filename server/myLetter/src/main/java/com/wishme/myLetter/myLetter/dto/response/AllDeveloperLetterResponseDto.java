package com.wishme.myLetter.myLetter.dto.response;

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
    // 보내는 사람 닉네임
    private String fromUserNickname;
    // 공개여부
    private boolean isPublic;
    // 에셋 이미지
    private String assetImg;
    // 로그인한 유저가 개발자인지
    private Boolean developer;


}
