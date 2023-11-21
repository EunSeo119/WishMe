package com.wishme.myLetter.myLetter.controller;

import com.wishme.myLetter.myLetter.dto.request.WriteDeveloperLetterRequestDto;
import com.wishme.myLetter.myLetter.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/developer/letter")
public class DeveloperController {

    private final DeveloperService developerService;

    // API 1. 개발자 편지 작성
    @PostMapping("/write")
    public ResponseEntity<?> writeDeveloperLetter(Authentication authentication, @RequestBody WriteDeveloperLetterRequestDto writeDeveloperLetterRequestDto) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(developerService.writeDeveloperLetter(authentication, writeDeveloperLetterRequestDto));

    }

    // API 2. 개발자 책상 확인
    // 리팩토링 코드
    @GetMapping("/all")
    public ResponseEntity<?> allDeveloperLetter(Authentication authentication, @PageableDefault(page = 0, size = 9, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK)
                .body(developerService.allDeveloperLetter(authentication, pageable));
    }

    //API 3. 개발자 편지 상세 조회
    @GetMapping("/one/{myLetterId}")
    public ResponseEntity<?> oneDeveloperLetter(Authentication authentication, @PathVariable("myLetterId") Long myLetterId) throws Exception {

        return ResponseEntity.status(HttpStatus.OK)
                .body(developerService.oneDeveloperLetter(authentication, myLetterId));
    }

    /**
     * 답장에서 내가 썼던 편지 가기
     */
    @GetMapping("/detail/{myLetterSeq}")
    public ResponseEntity<?> getDeveloperLetterDetail(Authentication authentication, @PathVariable("myLetterSeq") Long myLetterSeq) throws Exception {

        return ResponseEntity.status(HttpStatus.OK)
                .body(developerService.getDeveloperLetterDetail(authentication, myLetterSeq));
    }
}
