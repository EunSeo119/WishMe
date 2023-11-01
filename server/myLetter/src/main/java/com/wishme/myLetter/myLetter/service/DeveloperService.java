package com.wishme.myLetter.myLetter.service;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.asset.repository.AssetRepository;
import com.wishme.myLetter.config.RSAUtil;
import com.wishme.myLetter.myLetter.dto.request.WriteDeveloperLetterRequestDto;
import com.wishme.myLetter.myLetter.dto.response.AllDeveloperLetterListResponseDto;
import com.wishme.myLetter.myLetter.dto.response.AllDeveloperLetterResponseDto;
import com.wishme.myLetter.myLetter.dto.response.OneDeveloperLetterResponseDto;
import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.repository.DeveloperRepository;
import com.wishme.myLetter.user.domain.User;
import com.wishme.myLetter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeveloperService {

    @Value("${key.Base64_Public_Key}")
    String publicKeyBase;

    @Value("${key.Base64_Private_Key}")
    String privateKeyBase;

    private final DeveloperRepository developerRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;

    // 개발자 편지 작성
    public void writeDeveloperLetter(Authentication authentication, WriteDeveloperLetterRequestDto writeDeveloperLetterRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        User admin = userRepository.findById(1L).orElse(null);
        Asset asset = assetRepository.findById(writeDeveloperLetterRequestDto.getAssetSeq()).orElse(null);

        //base64된 공개키를 가져옴
        PublicKey publicKey = RSAUtil.getPublicKeyFromBase64String(publicKeyBase);

        //공개키로 암호화
        String encryptedContent = RSAUtil.encryptRSA(writeDeveloperLetterRequestDto.getContent(), publicKey);

        Long fromUserLong = null;
        if(authentication != null) {
            fromUserLong = Long.parseLong(authentication.getName());
        }

        if(admin != null && asset != null){
            MyLetter myLetter = MyLetter.builder()
                    .toUser(admin)
                    .asset(asset)
                    .content(encryptedContent)
                    .fromUserNickname(writeDeveloperLetterRequestDto.getNickname())
                    .fromUser(fromUserLong)
                    .isPublic(writeDeveloperLetterRequestDto.isPublic())
                    .build();
            developerRepository.save(myLetter);
        }else{
            throw new IllegalArgumentException("개별자 편지 작성 실패");
        }
    }

    // 개발자 책상 확인
    public AllDeveloperLetterListResponseDto allDeveloperLetter(Pageable pageable, int page){
        User admin = userRepository.findById(1L).orElse(null);

        // 페이지 번호 사용해 Pageable 수정
        pageable = PageRequest.of(page-1, pageable.getPageSize(), pageable.getSort());

        Page<MyLetter> myLetters = developerRepository.findAllDeveloperLetter(pageable, admin);

        if(admin != null){
            // 9개씩 담기
            List<AllDeveloperLetterResponseDto> developerLetterResponseDtos = new ArrayList<>();
            for(MyLetter myLetter : myLetters){
                AllDeveloperLetterResponseDto result = AllDeveloperLetterResponseDto.builder()
                        .myLetterSeq(myLetter.getMyLetterSeq())
                        .assetSeq(myLetter.getAsset().getAssetSeq())
                        .fromUserNickname(myLetter.getFromUserNickname())
                        .isPublic(myLetter.getIsPublic())
                        .build();
                developerLetterResponseDtos.add(result);
            }
            // 총 편지 수, 총 페이지 수, 페이지 당 편지
            return AllDeveloperLetterListResponseDto.builder()
                    .totalLetters(myLetters.getNumberOfElements())
                    .totalPages(myLetters.getTotalPages())
                    .lettersPerPage(developerLetterResponseDtos)
                    .build();
        }else{
            throw new IllegalArgumentException("개별자 편지 전체 조회 실패");
        }
    }

    // 개발자 편지 상세 조회
    public OneDeveloperLetterResponseDto oneDeveloperLetter(Long myLetterId) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, BadPaddingException, InvalidKeyException {
        MyLetter myLetter = developerRepository.findById(myLetterId).orElse(null);

        if(myLetter.getToUser().getUserSeq() != 1l) {
            throw new IllegalArgumentException("개발자 편지가 아닙니다.");
        }

        if(!myLetter.getIsPublic()) {
            throw new IllegalArgumentException("해당 편지는 비공개 편지 입니다.");
        }

        // 개인키로 복호화
        PrivateKey privateKey = RSAUtil.getPrivateKeyFromBase64String(privateKeyBase);
        String decryptContent = RSAUtil.decryptRSA(myLetter.getContent(), privateKey);

        if(myLetter != null && myLetter.getIsPublic()){
            return OneDeveloperLetterResponseDto.builder()
                    .assetSeq(myLetter.getAsset().getAssetSeq())
                    .content(decryptContent)
                    .nickname(myLetter.getFromUserNickname())
                    .fromUser(myLetter.getFromUser())
                    .createAt(myLetter.getCreateAt())
                    .assetImg(myLetter.getAsset().getAssetImg())
                    .build();
        }else{
            throw new IllegalArgumentException("개별자 편지 상세 조회 실패");
        }
    }
}
