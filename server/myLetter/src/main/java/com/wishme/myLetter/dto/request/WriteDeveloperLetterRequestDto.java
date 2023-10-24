package com.wishme.myLetter.dto.request;

import com.wishme.asset.domain.Asset;
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
    private Asset assetSeq;
    // 내용
    private String content;
    // 닉네임
    private String nickname;
    // 보내는 사람
    private Long toUser;

}
