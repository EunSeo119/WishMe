package com.wishme.myLetter.myLetter.service;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.myLetter.dto.response.MyLetterAssetResponseDto;
import com.wishme.myLetter.myLetter.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyLetterService {

    private final AssetRepository assetRepository;

    public List<MyLetterAssetResponseDto> getMyLetterAssets() {
        List<MyLetterAssetResponseDto> result = new ArrayList<>();

        List<Asset> assetList = assetRepository.findAllByOrderByAssetSeq();
        for(Asset asset : assetList) {
            MyLetterAssetResponseDto myLetterAssetResponseDto = MyLetterAssetResponseDto.builder()
                    .assetSeq(asset.getAssetSeq())
                    .assetImg(asset.getAssetImg())
                    .build();

            result.add(myLetterAssetResponseDto);
        }

        return result;
    }
}
