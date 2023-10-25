package com.wishme.myLetter.myLetter.service;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.myLetter.repository.AssetRepository;
import com.wishme.myLetter.myLetter.dto.request.WriteDeveloperLetterRequestDto;
import com.wishme.myLetter.myLetter.dto.response.AllDeveloperLetterResponseDto;
import com.wishme.myLetter.myLetter.dto.response.OneDeveloperLetterResponseDto;
import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.repository.DeveloperRepository;
import com.wishme.myLetter.user.domain.User;
import com.wishme.myLetter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;

    // 개발자 편지 작성
    public void writeDeveloperLetter(Authentication authentication, WriteDeveloperLetterRequestDto writeDeveloperLetterRequestDto){

        User admin = userRepository.findById(1L).orElse(null);
        Asset asset = assetRepository.findById(writeDeveloperLetterRequestDto.getAssetSeq()).orElse(null);

        if(admin != null && asset != null){
            MyLetter myLetter = MyLetter.builder()
                    .toUser(admin)
                    .asset(asset)
                    .content(writeDeveloperLetterRequestDto.getContent())
                    .fromUserNickname(writeDeveloperLetterRequestDto.getNickname())
                    .fromUser(Long.parseLong(authentication.getName()))
                    .isPublic(writeDeveloperLetterRequestDto.isPublic())
                    .build();
            developerRepository.save(myLetter);
        }else{
            throw new IllegalArgumentException("개별자 편지 작성 실패");
        }
    }

    // 개발자 책상 확인
    public List<AllDeveloperLetterResponseDto> allDeveloperLetter(){
        User admin = userRepository.findById(1L).orElse(null);
        List<MyLetter> myLetters = developerRepository.findByToUser(admin);

        if(admin != null){
            List<AllDeveloperLetterResponseDto> developerLetterResponseDtos = new ArrayList<>();
            for(MyLetter myLetter : myLetters){
                AllDeveloperLetterResponseDto result = AllDeveloperLetterResponseDto.builder()
                        .myLetterSeq(myLetter.getMyLetterSeq())
                        .assetSeq(myLetter.getAsset().getAssetSeq())
                        .nickname(myLetter.getFromUserNickname())
                        .build();
                developerLetterResponseDtos.add(result);
            }
            return developerLetterResponseDtos;
        }else{
            throw new IllegalArgumentException("개별자 편지 전체 조회 실패");
        }
    }

    // 개발자 편지 상세 조회
    public OneDeveloperLetterResponseDto oneDeveloperLetter(Authentication authentication, Long myLetterId){
        MyLetter myLetter = developerRepository.findById(myLetterId).orElse(null);
        if(myLetter != null && myLetter.getIsPublic()){
            return OneDeveloperLetterResponseDto.builder()
                    .assetSeq(myLetter.getAsset().getAssetSeq())
                    .content(myLetter.getContent())
                    .nickname(myLetter.getFromUserNickname())
                    .fromUser(myLetter.getFromUser())
                    .createAt(myLetter.getCreateAt())
                    .build();
        }else{
            throw new IllegalArgumentException("개별자 편지 상세 조회 실패");
        }
    }
}
