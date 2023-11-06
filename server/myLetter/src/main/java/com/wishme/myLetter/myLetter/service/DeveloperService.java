package com.wishme.myLetter.myLetter.service;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.asset.repository.AssetRepository;
import com.wishme.myLetter.myLetter.dto.request.WriteDeveloperLetterRequestDto;
import com.wishme.myLetter.myLetter.dto.response.AllDeveloperLetterListResponseDto;
import com.wishme.myLetter.myLetter.dto.response.AllDeveloperLetterResponseDto;
import com.wishme.myLetter.myLetter.dto.response.OneDeveloperLetterResponseDto;
import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.repository.DeveloperRepository;
import com.wishme.myLetter.myLetter.repository.MyLetterRepository;
import com.wishme.myLetter.user.domain.User;
import com.wishme.myLetter.user.repository.UserRepository;
import com.wishme.myLetter.util.AES256;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Value("${key.AES256_Key}")
    String key;

    private final DeveloperRepository developerRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final MyLetterRepository myLetterRepository;

    // 개발자 편지 작성
    public void writeDeveloperLetter(Authentication authentication, WriteDeveloperLetterRequestDto writeDeveloperLetterRequestDto) throws Exception {

        User admin = userRepository.findById(1L).orElse(null);
        Asset asset = assetRepository.findById(writeDeveloperLetterRequestDto.getAssetSeq()).orElse(null);

        AES256 aes256 = new AES256(key);
        String cipherContent = aes256.encrypt(writeDeveloperLetterRequestDto.getContent());

//        //base64된 공개키를 가져옴
//        PublicKey publicKey = RSAUtil.getPublicKeyFromBase64String(publicKeyBase);
//
//        //공개키로 암호화
//        String encryptedContent = RSAUtil.encryptRSA(writeDeveloperLetterRequestDto.getContent(), publicKey);

        Long fromUserLong = null;
        if(authentication != null) {
            fromUserLong = Long.parseLong(authentication.getName());
        }

        if(admin != null && asset != null){
            MyLetter myLetter = MyLetter.builder()
                    .toUser(admin)
                    .asset(asset)
                    .content(cipherContent)
                    .fromUserNickname(writeDeveloperLetterRequestDto.getNickname())
                    .fromUser(fromUserLong)
                    .isPublic(writeDeveloperLetterRequestDto.getIsPublic())
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
        Sort sort = Sort.by(Sort.Order.desc("createAt"));
        pageable = PageRequest.of(page-1, 9, sort);

        List<MyLetter> myLetters = myLetterRepository.findAllByToUser(admin, pageable);
        Integer totalCnt = developerRepository.findTotalCnt(admin);

        // totalCnt가 null이 아닐 때만 연산을 수행합니다.
        int totalPage = 0;
        if (totalCnt != null) {
            totalPage = Math.round(totalCnt / 9.0f);
        }

        if(admin != null){
            // 9개씩 담기
            List<AllDeveloperLetterResponseDto> developerLetterResponseDtos = new ArrayList<>();
            for(MyLetter myLetter : myLetters){
                AllDeveloperLetterResponseDto result = AllDeveloperLetterResponseDto.builder()
                        .myLetterSeq(myLetter.getMyLetterSeq())
                        .assetSeq(myLetter.getAsset().getAssetSeq())
                        .fromUserNickname(myLetter.getFromUserNickname())
                        .isPublic(myLetter.getIsPublic())
                        .assetImg(myLetter.getAsset().getAssetImg())
                        .build();
                developerLetterResponseDtos.add(result);
            }
            // 총 편지 수, 총 페이지 수, 페이지 당 편지
            return AllDeveloperLetterListResponseDto.builder()
                    .totalLetters(totalCnt)
                    .totalPages(totalPage)
                    .lettersPerPage(developerLetterResponseDtos)
                    .build();
        }else{
            throw new IllegalArgumentException("개별자 편지 전체 조회 실패");
        }
    }

    // 개발자 편지 상세 조회
    public OneDeveloperLetterResponseDto oneDeveloperLetter(Authentication authentication, Long myLetterId) throws Exception {
        MyLetter myLetter = developerRepository.findById(myLetterId)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 편지는 존재하지 않습니다.", 1));

        User checkUser = null;
        if(authentication != null){
            checkUser = userRepository.findByUserSeq(Long.valueOf(authentication.getName()))
                    .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));
        }

        if(myLetter.getToUser().getUserSeq() != 1l) {
            throw new IllegalArgumentException("개발자 편지가 아닙니다.");
        }

        if(!myLetter.getToUser().equals(checkUser) && !myLetter.getIsPublic()) {
            throw new RuntimeException("비공개 편지입니다.");
        }

//        if(!myLetter.getIsPublic()) {
//            throw new IllegalArgumentException("해당 편지는 비공개 편지 입니다.");
//        }

        AES256 aes256 = new AES256(key);
        String decryptContent = aes256.decrypt(myLetter.getContent());
//        // 개인키로 복호화
//        PrivateKey privateKey = RSAUtil.getPrivateKeyFromBase64String(privateKeyBase);
//        String decryptContent = RSAUtil.decryptRSA(myLetter.getContent(), privateKey);

        if(myLetter != null){
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
