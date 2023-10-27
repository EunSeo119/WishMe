package com.wishme.myLetter.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wishme.myLetter.user.domain.User;
import com.wishme.myLetter.user.dto.request.KakaoUserInfoDto;
import com.wishme.myLetter.user.repository.UserRepository;
import com.wishme.myLetter.util.JwtUtil;
import com.wishme.myLetter.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoServiceImpl implements KakaoService {

    @Value("{jwt.secret.key}")
    private String secretKey;

    private final KakaoUtil kakaoUtil;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> login(String code, HttpServletResponse response) throws JsonProcessingException {

        // 1. 인가 코드로 액세스 토큰 요청
        String accessToken = kakaoUtil.getAccessToken(code);

        // 2. 액세스 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfoDto = kakaoUtil.getKakaoUserInfo(accessToken);

        // 3. 카카오 ID로 회원가입 처리
        User user = userRepository.findByEmail(kakaoUserInfoDto.getEmail())
                .orElse(null);

        if (user == null) {
            // 회원가입
            String email = kakaoUserInfoDto.getEmail();
            String userNickname = kakaoUserInfoDto.getNickname();
            String uuid = UUID.randomUUID().toString();

            user = new User(email, userNickname, uuid);
            userRepository.save(user);
        }

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        JwtUtil jwtUtil = new JwtUtil();

        try {
            // 4. 로그인 처리 & Response Header 에 JWT 추가
            Map<String, Object> data = new HashMap<>();
            data.put("token", jwtUtil.createJwt(Long.toString(user.getUserSeq()), secretKey));

            resultMap.put("data", data);
            resultMap.put("message", "로그인 성공");
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("error", e.getMessage());
            resultMap.put("message", "로그인 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultMap, status);
    }
}
