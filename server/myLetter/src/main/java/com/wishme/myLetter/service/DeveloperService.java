package com.wishme.myLetter.service;

import com.wishme.myLetter.domain.MyLetter;
import com.wishme.myLetter.dto.request.WriteDeveloperLetterRequestDto;
import com.wishme.myLetter.repository.DeveloperRepository;
import com.wishme.user.domain.User;
import com.wishme.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final UserRepository userRepository;

    // 개발자 편지 작성
    public void writeDeveloperLetter(Authentication authentication, WriteDeveloperLetterRequestDto writeDeveloperLetterRequestDto){

        User admin = userRepository.findById(1L).orElse(null);

        if(admin != null){
            MyLetter myLetter = MyLetter.builder()
                    .fromUser(admin)
                    .assetSeq(writeDeveloperLetterRequestDto.getAssetSeq())
                    .content(writeDeveloperLetterRequestDto.getContent())
                    .nickname(writeDeveloperLetterRequestDto.getNickname())
                    .toUser(Long.parseLong(authentication.getName()))
                    .isPublic(true)
                    .build();
            developerRepository.save(myLetter);
        }else{
            throw new IllegalArgumentException("개별자 편지 작성 실패");
        }
    }

}
