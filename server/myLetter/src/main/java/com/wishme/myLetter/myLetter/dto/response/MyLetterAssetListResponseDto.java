package com.wishme.myLetter.myLetter.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyLetterAssetListResponseDto {

    private List<MyLetterAssetResponseDto> assetList;
}
