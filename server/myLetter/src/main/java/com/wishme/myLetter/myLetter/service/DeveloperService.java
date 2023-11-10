package com.wishme.myLetter.myLetter.service;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.asset.repository.AssetRepository;
import com.wishme.myLetter.common.exception.CustomException;
import com.wishme.myLetter.common.exception.code.ErrorCode;
import com.wishme.myLetter.myLetter.domain.Reply;
import com.wishme.myLetter.myLetter.dto.request.WriteDeveloperLetterRequestDto;
import com.wishme.myLetter.myLetter.dto.response.AllDeveloperLetterListResponseDto;
import com.wishme.myLetter.myLetter.dto.response.AllDeveloperLetterResponseDto;
import com.wishme.myLetter.myLetter.dto.response.MyLetterDetailResponseDto;
import com.wishme.myLetter.myLetter.dto.response.OneDeveloperLetterResponseDto;
import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.repository.DeveloperRepository;
import com.wishme.myLetter.myLetter.repository.MyLetterRepository;
import com.wishme.myLetter.myLetter.repository.ReplyRepository;
import com.wishme.myLetter.user.domain.User;
import com.wishme.myLetter.user.repository.UserRepository;
import com.wishme.myLetter.util.AES256;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final ReplyRepository replyRepository;

    // 개발자 편지 작성
    public Long writeDeveloperLetter(Authentication authentication, WriteDeveloperLetterRequestDto writeDeveloperLetterRequestDto) throws Exception {

        User admin = userRepository.findById(1L)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));
        Asset asset = assetRepository.findById(writeDeveloperLetterRequestDto.getAssetSeq())
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 에셋은 존재하지 않습니다.", 1));

        // 편지 내용 암호화
        AES256 aes256 = new AES256(key);
        String cipherContent = aes256.encrypt(writeDeveloperLetterRequestDto.getContent());

        // 로그인 여부 확인
        Long fromUserLong = null;
        if(authentication != null) {
            fromUserLong = Long.parseLong(authentication.getName());
        }

        MyLetter myLetter = MyLetter.builder()
                .toUser(admin)
                .asset(asset)
                .content(cipherContent)
                .fromUserNickname(writeDeveloperLetterRequestDto.getNickname())
                .fromUser(fromUserLong)
                .isPublic(writeDeveloperLetterRequestDto.getIsPublic())
                .build();
        MyLetter save = developerRepository.save(myLetter);
        return save.getMyLetterSeq();
    }

    // 개발자 책상 확인
    // 리팩토링 코드
    public AllDeveloperLetterListResponseDto allDeveloperLetter(Authentication authentication, Pageable pageable){

        User admin = userRepository.findByUserSeq(1L)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

        Page<MyLetter> myLetterPage = developerRepository.findDeveloperLetterByToUser(admin, pageable)
                .orElseThrow(() -> new CustomException(ErrorCode.LETTER_NOT_FOUND_ERROR));

        List<MyLetter> myLetterList = myLetterPage.getContent();

        // 로그인한 유저가 개발자면 무조건 열람 가능
        boolean isDeveloper = false;
        if(authentication != null && Long.parseLong(authentication.getName()) == 1){
            isDeveloper = true;
        }

        // 9개씩 담기
        List<AllDeveloperLetterResponseDto> developerLetterResponseDtos = new ArrayList<>();
        for(MyLetter myLetter : myLetterList){
            AllDeveloperLetterResponseDto result = AllDeveloperLetterResponseDto.builder()
                    .myLetterSeq(myLetter.getMyLetterSeq())
                    .assetSeq(myLetter.getAsset().getAssetSeq())
                    .fromUserNickname(myLetter.getFromUserNickname())
                    .isPublic(myLetter.getIsPublic())
                    .developer(isDeveloper)
                    .assetImg(myLetter.getAsset().getAssetImg())
                    .build();
            developerLetterResponseDtos.add(result);
        }
        // 총 편지 수, 총 페이지 수, 페이지 당 편지
        return AllDeveloperLetterListResponseDto.builder()
                .totalLetters(myLetterPage.getTotalElements())
                .totalPages(myLetterPage.getTotalPages())
                .lettersPerPage(developerLetterResponseDtos)
                .build();
    }

    // 개발자 편지 상세 조회
    public OneDeveloperLetterResponseDto oneDeveloperLetter(Authentication authentication, Long myLetterId) throws Exception {

        MyLetter myLetter = developerRepository.findById(myLetterId)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 편지는 존재하지 않습니다.", 1));

        User checkUser = null;
        if(authentication != null){
            checkUser = userRepository.findByUserSeq(Long.parseLong(authentication.getName()))
                    .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));
        }

        // 개발자 편지가 아닌 경우
        if(myLetter.getToUser().getUserSeq() != 1l) {
            throw new CustomException(ErrorCode.NOT_DEVELOPER_LETTER_ERROR);
        }

        // 로그인한 사용사에게 온 편지가 아니고, 비공개 편지인 경우
        if(!myLetter.getToUser().equals(checkUser) && !myLetter.getIsPublic()) {
            throw new CustomException(ErrorCode.PRIVATE_LETTER_ERROR);
        }

        // 현재 로그인한 유저가 개발자인 경우
        boolean isMine = false;
        if(authentication != null){
            if(Long.parseLong(authentication.getName()) == 1){
                isMine = true;
            }
        }

        // fromUser가 있고, 답장 한 적 없어 답장할 수 있는 경우
        boolean canReply = false;
        Optional<Reply> replyOptional = replyRepository.findByLetter(myLetter);
        if(myLetter.getFromUser() != null && !replyOptional.isPresent()){
            canReply = true;
        }

        // 편지 내용 복호화
        AES256 aes256 = new AES256(key);
        String decryptContent = aes256.decrypt(myLetter.getContent());

        return OneDeveloperLetterResponseDto.builder()
                .assetSeq(myLetter.getAsset().getAssetSeq())
                .content(decryptContent)
                .nickname(myLetter.getFromUserNickname())
                .fromUser(myLetter.getFromUser())
                .createAt(myLetter.getCreateAt())
                .assetImg(myLetter.getAsset().getAssetImg())
                .isMine(isMine)
                .canReply(canReply)
                .build();
    }

    public MyLetterDetailResponseDto getDeveloperLetterDetail(Authentication authentication, Long myLetterSeq) throws Exception{
        MyLetter myletter = myLetterRepository.findByMyLetterSeq(myLetterSeq)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 편지는 존재하지 않습니다.", 1));

        Boolean canReply = false;

        if(authentication == null) {
            // 비공개 편지인데 비회원이 열람하려고 할 때
            if(!myletter.getIsPublic()) {
                throw new RuntimeException("열람할 권한이 없습니다.");
            }
        } else {
            User checkUser = userRepository.findByUserSeq(Long.valueOf(authentication.getName()))
                    .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

            // 비공개 편지인데 해당 편지를 쓴 사람이 내가 아닐 때
            if(!myletter.getIsPublic() && !myletter.getFromUser().equals(checkUser.getUserSeq())) {
                throw new RuntimeException("열람할 권한이 없습니다.");
            }

            // 이 편지에 답장이 가능할 때
            if(myletter.getToUser().equals(checkUser) && myletter.getFromUser() != null
                    && replyRepository.findByMyLetter(myletter) == null) {
                canReply = true;
            }
        }

        AES256 aes256 = new AES256(key);
        String decryptContent = aes256.decrypt(myletter.getContent());

        return MyLetterDetailResponseDto.builder()
                .myLetterSeq(myletter.getMyLetterSeq())
                .toUserNickname(myletter.getToUser().getUserNickname())
                .content(decryptContent)
                .fromUser(myletter.getFromUser())
                .fromUserNickname(myletter.getFromUserNickname())
                .canReply(canReply)
                .build();
    }
}
