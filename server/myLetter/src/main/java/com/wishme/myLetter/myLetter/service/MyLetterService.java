package com.wishme.myLetter.myLetter.service;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.dto.request.SaveMyLetterRequestDto;
import com.wishme.myLetter.myLetter.dto.response.*;
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
    public Long saveLetter(Authentication authentication, SaveMyLetterRequestDto saveMyLetterRequestDto) {
        Long fromUserSeq = null;
        if(authentication != null) {
            fromUserSeq = Long.valueOf(authentication.getName());
        }

        User toUser = userRepository.findByUuid(saveMyLetterRequestDto.getToUserUuid())
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다. 해당 유저에게 편지를 쓸 수 없습니다.", 1));

        Asset myAsset = assetRepository.findByAssetSeqAndType(saveMyLetterRequestDto.getAssetSeq(), 'M')
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 에셋는 존재하지 않습니다.", 1));

        MyLetter myLetter = MyLetter.builder()
                .toUser(toUser)
                .asset(myAsset)
                .content(saveMyLetterRequestDto.getContent())
                .fromUserNickname(saveMyLetterRequestDto.getFromUserNickname())
                .fromUser(fromUserSeq)
                .isPublic(saveMyLetterRequestDto.getIsPublic())
                .build();

        MyLetter save = myLetterRepository.save(myLetter);
        return save.getMyLetterSeq();
    }

    public MyLetterListResponseDto getMyLetterList(Authentication authentication, String userUuid, int page) {
        User toUser = userRepository.findByUuid(userUuid)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

        boolean isMine = false;
        long totalLetterCount = myLetterRepository.countByToUser(toUser);

        // 회원일 때 자기 책상인지 남의 책상인지 확인
        if(authentication != null) {
            User requestUser = userRepository.findByUserSeq(Long.valueOf(authentication.getName()))
                    .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

            if(toUser.equals(requestUser)) {
                isMine = true;
            }
        }

        // 9개씩 페이징 처리해줌
        PageRequest pageRequest = PageRequest.of(page-1, 9);

        List<MyLetter> myLetters = myLetterRepository.findAllByToUser(toUser, pageRequest);

        List<MyLetterResponseDto> myLetterResponseDtoList = new ArrayList<>();

        for(MyLetter letter : myLetters) {
            Asset myAsset = assetRepository.findByAssetSeqAndType(letter.getAsset().getAssetSeq(), 'M')
                    .orElseThrow(() -> new EmptyResultDataAccessException("해당 에셋는 존재하지 않습니다.", 1));

            MyLetterResponseDto myLetterResponseDto = MyLetterResponseDto.builder()
                    .myLetterSeq(letter.getMyLetterSeq())
                    .fromUserNickname(letter.getFromUserNickname())
                    .assetImg(myAsset.getAssetImg())
                    .isPublic(letter.getIsPublic())
                    .build();

            myLetterResponseDtoList.add(myLetterResponseDto);
        }

        MyLetterListResponseDto myLetterListResponseDto = MyLetterListResponseDto.builder()
                .isMine(isMine)
                .totalLetterCount(totalLetterCount)
                .toUserSeq(toUser.getUserSeq())
                .toUserNickname(toUser.getFromUserNickname())
                .myLetterResponseDtoList(myLetterResponseDtoList)
                .build();

        return myLetterListResponseDto;
    }

    public LoginUserUuidResponseDto getUserUuid(Authentication authentication) {
        User user = userRepository.findByUserSeq(Long.valueOf(authentication.getName()))
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

        LoginUserUuidResponseDto loginUserUuidResponseDto = LoginUserUuidResponseDto.builder()
                .loginUserUuid(user.getUuid())
                .build();
        return loginUserUuidResponseDto;
    }

    public MyLetterDetailResponseDto getMyLetterDetail(Authentication authentication, Long myLetterSeq) {

        MyLetter myletter = myLetterRepository.findByMyLetterSeq(myLetterSeq)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 편지는 존재하지 않습니다.", 1));

        User checkUser = userRepository.findByUserSeq(Long.valueOf(authentication.getName()))
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

        if(!myletter.getToUser().equals(checkUser) && !myletter.getIsPublic()) {
            throw new RuntimeException("열람할 권한이 없습니다.");
        }

        return MyLetterDetailResponseDto.builder()
                .myLetterSeq(myletter.getMyLetterSeq())
                .toUserNickname(checkUser.getFromUserNickname())
                .content(myletter.getContent())
                .fromUser(myletter.getFromUser())
                .fromUserNickname(myletter.getFromUserNickname())
                .build();
    }
}
