package com.wishme.user.user.controller;

import com.wishme.user.user.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // API 1. 카카오 로그인
    @PostMapping("/login")
    public ResponseEntity<?> kakaoLogin() {
        return null;
    }

    // API 2. 닉네임 수정
    @PutMapping("/nickname")
    public ResponseEntity<?> modifyNickname(@RequestBody Map<String, String> request, Authentication authentication) {
        return userService.modifyNickname(request, Long.parseLong(authentication.getName()));
    }

    // API 3. 학교 등록
    @PostMapping("/school")
    public ResponseEntity<?> registerSchool(@RequestBody Map<String, String> request, Authentication authentication) {
        return userService.registerSchool(request, Long.parseLong(authentication.getName()));
    }

    // API 4. 학교 수정
    @PutMapping("/school")
    public ResponseEntity<?> modifySchool(@RequestBody Map<String, String> request, Authentication authentication) {
        return userService.modifySchool(request, Long.parseLong(authentication.getName()));
    }

    // API 5. 학교 검색
    @GetMapping("/school")
    public ResponseEntity<?> searchSchool(@RequestBody Map<String, String> request) {
        return userService.searchSchool(request);
    }
}
