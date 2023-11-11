package com.wishme.myLetter.myLetter.service;

import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.asset.repository.AssetRepository;
import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.dto.request.SaveMyLetterRequestDto;
import com.wishme.myLetter.myLetter.dto.response.*;
import com.wishme.myLetter.myLetter.repository.MyLetterRepository;
import com.wishme.myLetter.myLetter.repository.ReplyRepository;
import com.wishme.myLetter.openAPI.dto.request.GPTCompletionChatRequestDto;
import com.wishme.myLetter.openAPI.service.GPTService;
import com.wishme.myLetter.user.domain.User;
import com.wishme.myLetter.user.repository.UserRepository;
import com.wishme.myLetter.util.AES256;
import com.wishme.myLetter.util.BadWordFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyLetterService {

    @Value("${key.Base64_Public_Key}")
    String publicKeyBase;

    @Value("${key.Base64_Private_Key}")
    String privateKeyBase;

    @Value("${key.AES256_Key}")
    String key;

    @Value("${enable_date.year}")
    String enableYear;

    @Value("${enable_date.month}")
    String enableMonth;

    @Value("${enable_date.day}")
    String enableDay;

    private final AssetRepository assetRepository;
    private final MyLetterRepository myLetterRepository;
    private final UserRepository userRepository;
    private final GPTService gptService;
    private final ReplyRepository replyRepository;

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
    public Long saveLetter(Authentication authentication, SaveMyLetterRequestDto saveMyLetterRequestDto) throws Exception {
        Long fromUserSeq = null;
        if(authentication != null) {
            fromUserSeq = Long.valueOf(authentication.getName());
        }

        User toUser = userRepository.findByUuid(saveMyLetterRequestDto.getToUserUuid())
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다. 해당 유저에게 편지를 쓸 수 없습니다.", 1));

        Asset myAsset = assetRepository.findByAssetSeqAndType(saveMyLetterRequestDto.getAssetSeq(), 'M')
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 에셋는 존재하지 않습니다.", 1));

        String gptResult = null;
        boolean isBad = false;
        do {
            GPTCompletionChatRequestDto gptCompletionChatRequestDto = GPTCompletionChatRequestDto.builder()
                    .message(saveMyLetterRequestDto.getContent())
                    .build();

            gptResult = gptService.completionChat(gptCompletionChatRequestDto);
            if(gptResult == "bad") {
                isBad = true;
            }
//            System.out.println("===gpt===");
//            System.out.println(gptResult);
        } while(gptResult == "timeout");

        BadWordFilterUtil badWordFiltering = new BadWordFilterUtil("♡");
        String filteringNickname = badWordFiltering.change(saveMyLetterRequestDto.getFromUserNickname());
        String filteringContent = badWordFiltering.change(saveMyLetterRequestDto.getContent());

        AES256 aes256 = new AES256(key);
        String cipherContent = aes256.encrypt(filteringContent);

        MyLetter myLetter = MyLetter.builder()
                .toUser(toUser)
                .asset(myAsset)
                .content(cipherContent)
                .fromUserNickname(filteringNickname)
                .fromUser(fromUserSeq)
                .isPublic(saveMyLetterRequestDto.getIsPublic())
                .isBad(isBad)
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

        // 9개씩 페이징 처리해줌 (최신 순으로 정렬)
        Sort sort = Sort.by(Sort.Order.desc("createAt"));
        Pageable pageable = PageRequest.of(page - 1, 9, sort);

        List<MyLetter> myLetters = myLetterRepository.findAllByToUserAndIsReportIsFalse(toUser, pageable);

        List<MyLetterResponseDto> myLetterResponseDtoList = new ArrayList<>();

        for(MyLetter letter : myLetters) {
            Asset myAsset = assetRepository.findByAssetSeqAndType(letter.getAsset().getAssetSeq(), 'M')
                    .orElseThrow(() -> new EmptyResultDataAccessException("해당 에셋는 존재하지 않습니다.", 1));

            MyLetterResponseDto myLetterResponseDto = MyLetterResponseDto.builder()
                    .myLetterSeq(letter.getMyLetterSeq())
                    .fromUserNickname(letter.getFromUserNickname())
                    .assetImg(myAsset.getAssetImg())
                    .isPublic(letter.getIsPublic())
                    .isBad(letter.isBad())
                    .build();

            myLetterResponseDtoList.add(myLetterResponseDto);
        }

        MyLetterListResponseDto myLetterListResponseDto = MyLetterListResponseDto.builder()
                .isMine(isMine)
                .totalLetterCount(totalLetterCount)
                .toUserSeq(toUser.getUserSeq())
                .toUserNickname(toUser.getUserNickname())
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

    public MyLetterDetailResponseDto getMyLetterDetail(Authentication authentication, Long myLetterSeq) throws Exception{

        // 현재 날짜 가져오기
        LocalDate currentDate = LocalDate.now();

        LocalDate enableDate = LocalDate.of(Integer.parseInt(enableYear), Integer.parseInt(enableMonth), Integer.parseInt(enableDay));

        // 설정된 날짜와 현재 날짜 비교
        if (currentDate.isBefore(enableDate)) {
            throw new RuntimeException("11월 11일 이전에는 호출할 수 없습니다.");
        }

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

            // 비공개 편지인데 해당 편지의 주인이 아닌 회원이 열람하려고 할 때
            if(!myletter.getIsPublic() && !myletter.getToUser().equals(checkUser)) {
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

//        PrivateKey prKey = RSAUtil.getPrivateKeyFromBase64String(privateKeyBase);
//        String decryptContent = RSAUtil.decryptRSA(myletter.getContent(), prKey);

        return MyLetterDetailResponseDto.builder()
                .myLetterSeq(myletter.getMyLetterSeq())
                .toUserNickname(myletter.getToUser().getUserNickname())
                .content(decryptContent)
                .fromUser(myletter.getFromUser())
                .fromUserNickname(myletter.getFromUserNickname())
                .canReply(canReply)
                .build();
    }

    @Transactional
    public Long reportLetter(Authentication authentication, Long letterSeq) {
        MyLetter myLetter = myLetterRepository.findByMyLetterSeq(letterSeq)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 편지는 존재하지 않습니다.", 1));

        User user = userRepository.findByUserSeq(Long.valueOf(authentication.getName()))
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

        if(!myLetter.getToUser().equals(user)) {
            throw new IllegalArgumentException("해당 유저는 신고할 권한이 없습니다.");
        }

        myLetter.updateReport();
        myLetterRepository.save(myLetter);

        return myLetter.getMyLetterSeq();
    }
}
