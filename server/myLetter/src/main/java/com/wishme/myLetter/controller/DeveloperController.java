package com.wishme.myLetter.controller;

import com.wishme.myLetter.dto.request.WriteDeveloperLetterRequestDto;
import com.wishme.myLetter.service.DeveloperService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> writeDeveloperLetter(Authentication authentication, WriteDeveloperLetterRequestDto writeDeveloperLetterRequestDto){
        try{
            developerService.writeDeveloperLetter(authentication, writeDeveloperLetterRequestDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("개발자 편지 작성 실패");
        }
    }

    // API 2. 개발자 책상 확인
    @GetMapping("/all")
    public ResponseEntity<?> allDeveloperLetter(){
        try{
            return ResponseEntity.ok(developerService.allDeveloperLetter());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("개발자 편지 전체 조회 실패");
        }
    }

    //API 3. 개발자 편지 상세 조회
    @GetMapping("/one/{myLetterId}")
    public ResponseEntity<?> oneDeveloperLetter(@PathVariable("myLetterId") Long myLetterId){
        try{
            return ResponseEntity.ok(developerService.oneDeveloperLetter(myLetterId));
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("개발자 편지 상세 조회 실패");
        }
    }
}
