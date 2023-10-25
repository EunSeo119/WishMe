package com.wishme.myLetter.myLetter.service;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.dto.request.SaveMyLetterRequestDto;
import com.wishme.myLetter.myLetter.dto.response.MyLetterAssetResponseDto;
import com.wishme.myLetter.myLetter.dto.response.MyLetterDetailResponseDto;
import com.wishme.myLetter.myLetter.dto.response.MyLetterResponseDto;
import com.wishme.myLetter.asset.repository.AssetRepository;
import com.wishme.myLetter.myLetter.repository.MyLetterRepository;
import com.wishme.myLetter.user.domain.User;
import com.wishme.myLetter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyLetterService {

    private final AssetRepository assetRepository;
    private final MyLetterRepository myLetterRepository;
    private final UserRepository userRepository;

    public List<MyLetterAssetResponseDto> getMyLetterAssets() {
        List<MyLetterAssetResponseDto> result = new ArrayList<>();

        List<Asset> assetList = assetRepository.findAllByTypeOrderByAssetSeq('M');
        for(Asset asset : assetList) {
            MyLetterAssetResponseDto myLetterAssetResponseDto = MyLetterAssetResponseDto.builder()
                    .assetSeq(asset.getAssetSeq())
                    .assetImg(asset.getAssetImg())
                    .build();

            result.add(myLetterAssetResponseDto);
        }

        return result;
    }

    @Transactional
    public Long saveLetter(SaveMyLetterRequestDto saveMyLetterRequestDto) {
        User toUser = userRepository.findByUserSeq(saveMyLetterRequestDto.getToUserSeq())
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다. 해당 유저에게 편지를 쓸 수 없습니다.", 1));

        Asset myAsset = assetRepository.findByAssetSeqAndType(saveMyLetterRequestDto.getAssetSeq(), 'M')
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 에셋는 존재하지 않습니다.", 1));

        MyLetter myLetter = MyLetter.builder()
                .toUser(toUser)
                .asset(myAsset)
                .content(saveMyLetterRequestDto.getContent())
                .fromUserNickname(saveMyLetterRequestDto.getFromUserNickname())
                .fromUser(saveMyLetterRequestDto.getFromUser()) // 시큐리티 적용되면 수정
                .isPublic(saveMyLetterRequestDto.getIsPublic())
                .build();

        MyLetter save = myLetterRepository.save(myLetter);
        return save.getMyLetterSeq();
    }

    public List<MyLetterResponseDto> getMyLetterList(Authentication authentication, int page) {
        // 시큐리티 설정 후 수정
        User toUser = userRepository.findByEmail("eun@naver.com")
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

        // 9개씩 페이징 처리해줌
        PageRequest pageRequest = PageRequest.of(page-1, 9);

        List<MyLetter> myLetters = myLetterRepository.findAllByToUser(toUser, pageRequest);

        List<MyLetterResponseDto> result = new ArrayList<>();

        for(MyLetter letter : myLetters) {
            Asset myAsset = assetRepository.findByAssetSeqAndType(letter.getAsset().getAssetSeq(), 'M')
                    .orElseThrow(() -> new EmptyResultDataAccessException("해당 에셋는 존재하지 않습니다.", 1));

            MyLetterResponseDto myLetterResponseDto = MyLetterResponseDto.builder()
                    .myLetterSeq(letter.getMyLetterSeq())
                    .fromUserNickname(letter.getFromUserNickname())
                    .assetSeq(myAsset.getAssetSeq())
                    .isPublic(letter.getIsPublic())
                    .build();

            result.add(myLetterResponseDto);
        }

        return result;
    }

    public MyLetterDetailResponseDto getMyLetterDetail(Authentication authentication, Long myLetterSeq) {

        MyLetter myletter = myLetterRepository.findByMyLetterSeq(myLetterSeq)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 편지는 존재하지 않습니다.", 1));

        // 시큐리티 설정 후 수정
        User checkUser = userRepository.findByEmail("eun@naver.com")
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

        if(!myletter.getToUser().equals(checkUser)) {
            throw new RuntimeException("열람할 권한이 없습니다.");
        }

        MyLetterDetailResponseDto myLetterDetailResponseDto = MyLetterDetailResponseDto.builder()
                .myLetterSeq(myletter.getMyLetterSeq())
                .toUserNickname(checkUser.getUserNickname())
                .content(myletter.getContent())
                .fromUser(myletter.getFromUser())
                .fromUserNickname(myletter.getFromUserNickname())
                .build();

        return myLetterDetailResponseDto;
    }
}
