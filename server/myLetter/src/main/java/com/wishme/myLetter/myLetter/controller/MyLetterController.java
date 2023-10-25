package com.wishme.myLetter.myLetter.controller;

import com.wishme.myLetter.myLetter.service.MyLetterService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
