package com.wishme.myLetter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OneDeveloperLetterResponseDto {

    // 에셋 일련번호
    private Long assetSeq;
    // 내용
    private String content;
    // 닉네임
    private String nickname;
    // 보내는 사람
    private Long fromUser;
    // 작성 일시
    private LocalDateTime createAt;

}
