package com.wishme.myLetter.myLetter.controller;

import com.wishme.myLetter.myLetter.dto.request.SaveMyLetterRequestDto;
import com.wishme.myLetter.myLetter.service.MyLetterService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Getter
@RequestMapping("/api/my/letter")
public class MyLetterController {

    private final MyLetterService myLetterService;

    /**
     * 개인 편지 에셋들 보기
     */
    @GetMapping("/assets")
    public ResponseEntity<?> getMyLetterAssets() throws Exception {

        return ResponseEntity.status(HttpStatus.OK)
                .body(myLetterService.getMyLetterAssets());
    }

    /**
     * 개인 편지 저장하기
     */
    @PostMapping("/write")
    public ResponseEntity<?> saveLetter(Authentication authentication,
                                        @Valid @RequestBody SaveMyLetterRequestDto saveMyLetterRequestDto) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(myLetterService.saveLetter(authentication, saveMyLetterRequestDto));
    }

    /**
     * 개인 편지 리스트 확인하기
     */
    @GetMapping("/all/{userUuid}")
    public ResponseEntity<?> getMyLetterList(Authentication authentication,
                                             @PathVariable("userUuid") String userUuid, @RequestParam(value = "page") int page) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(myLetterService.getMyLetterList(authentication, userUuid, page));
    }

    /**
     * 현재 로그인한 유저의 Uuid 가져오기
     */
    @GetMapping("/loginUserUuid")
    public ResponseEntity<?> getUserUuid(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(myLetterService.getUserUuid(authentication));
    }

    /**
     * 개인 편지 내용 확인하기
     */
    @GetMapping("/detail/{myLetterSeq}")
    public ResponseEntity<?> getMyLetterDetail(Authentication authentication, @PathVariable("myLetterSeq") Long myLetterSeq) throws Exception {

        return ResponseEntity.status(HttpStatus.OK)
                .body(myLetterService.getMyLetterDetail(authentication, myLetterSeq));
    }

    /**
     * 편지 신고하기
     */
    @PutMapping("/report/{letterSeq}")
    public ResponseEntity<?> reportLetter(Authentication authentication, @PathVariable("letterSeq") Long letterSeq) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(myLetterService.reportLetter(authentication, letterSeq));
    }

}
