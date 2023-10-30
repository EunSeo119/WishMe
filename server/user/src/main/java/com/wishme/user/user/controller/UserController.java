package com.wishme.user.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wishme.user.user.model.service.KakaoService;
import com.wishme.user.user.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final KakaoService kakaoService;
    private final UserService userService;

    // API 1. 카카오 로그인
    @GetMapping("/login")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.login(code, response);
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

    // API 6. 유저 정보 조회
    @GetMapping
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        return userService.getUserInfo(Long.parseLong(authentication.getName()));
    }
}
