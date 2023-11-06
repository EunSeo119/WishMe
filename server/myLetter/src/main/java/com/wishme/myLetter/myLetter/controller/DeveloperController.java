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
    public ResponseEntity<?> writeDeveloperLetter(Authentication authentication, @RequestBody WriteDeveloperLetterRequestDto writeDeveloperLetterRequestDto){
        try{
            developerService.writeDeveloperLetter(authentication, writeDeveloperLetterRequestDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("개발자 편지 작성 실패");
        }
    }

    // API 2. 개발자 책상 확인
    @GetMapping("/all")
    public ResponseEntity<?> allDeveloperLetter(@PageableDefault(size = 9)Pageable pageable,  @RequestParam int page){
        try{
            return ResponseEntity.ok(developerService.allDeveloperLetter(pageable, page));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("개발자 편지 전체 조회 실패");
        }
    }

    //API 3. 개발자 편지 상세 조회
    @GetMapping("/one/{myLetterId}")
    public ResponseEntity<?> oneDeveloperLetter(Authentication authentication, @PathVariable("myLetterId") Long myLetterId){
        try{
            return ResponseEntity.ok(developerService.oneDeveloperLetter(authentication, myLetterId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
