package com.wishme.user.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wishme.user.user.model.dto.request.SearchSchoolRequestDto;
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
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) throws Exception {
        return kakaoService.login(code, response);
    }

    // API 2. 회원정보 수정
    @PutMapping("/modify")
    public ResponseEntity<?> modifyUserInfo(@RequestBody Map<String, String> request, Authentication authentication) {
        return userService.modifyUserInfo(request, Long.parseLong(authentication.getName()));
    }

    // API 3. 학교 등록
    @PostMapping("/school")
    public ResponseEntity<?> registerSchool(@RequestBody Map<String, String> request, Authentication authentication) {
        return userService.registerSchool(request, Long.parseLong(authentication.getName()));
    }

    // API 4. 학교 검색
    @PostMapping("/search/school")
    public ResponseEntity<?> searchSchool(@RequestBody SearchSchoolRequestDto searchSchoolRequestDto) {
        return userService.searchSchool(searchSchoolRequestDto);
    }

    // API 5. 유저 정보 조회
    @GetMapping
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        return userService.getUserInfo(Long.parseLong(authentication.getName()));
    }

    // API 6. Refresh Token을 통한 Access Token 재발급
    @PostMapping("/refresh")
    public ResponseEntity<?> getAccessTokenByRefreshToken(@RequestHeader("refresh-token") String refreshToken) {
        return userService.getAccessTokenByRefreshToken(refreshToken);
    }
}
