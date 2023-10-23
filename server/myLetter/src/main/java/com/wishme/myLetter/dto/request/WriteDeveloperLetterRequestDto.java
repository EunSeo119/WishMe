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

    private Asset assetSeq;

    private String content;

    private String nickname;

    private Long toUser;

}
