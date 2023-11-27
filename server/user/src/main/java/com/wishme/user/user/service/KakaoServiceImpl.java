package com.wishme.user.user.service;

import com.wishme.user.user.domain.User;
import com.wishme.user.user.dto.request.KakaoUserInfoDto;
import com.wishme.user.user.repository.UserRepository;
import com.wishme.user.util.AES256;
import com.wishme.user.util.JwtUtil;
import com.wishme.user.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoServiceImpl implements KakaoService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${key.AES256_Key}")
    private String key;

    private final KakaoUtil kakaoUtil;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> login(String code, HttpServletResponse response) throws Exception {

        // 1. 인가 코드로 액세스 토큰 요청
        String accessToken = kakaoUtil.getAccessToken(code);

        // 2. 액세스 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfoDto = kakaoUtil.getKakaoUserInfo(accessToken);

        String email = kakaoUserInfoDto.getEmail();
        AES256 aes256 = new AES256(key);
        String cipherEmail = aes256.encrypt(email); // email AES256 암호화

        // 3. 카카오 ID로 회원가입 처리
        User user = userRepository.findByEmail(cipherEmail);
        if (user == null) {
            // 회원가입
//            String userNickname = kakaoUserInfoDto.getNickname();
            String userNickname = generateRandomNickname();
            String uuid = UUID.randomUUID().toString();

            user = new User(cipherEmail, userNickname, uuid);
            userRepository.save(user);
        }

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        JwtUtil jwtUtil = new JwtUtil();

        try {
            // 4. 로그인 처리 & Response Header 에 JWT AccessToken & JWT RefreshToken 추가
            String refreshToken = jwtUtil.createRefreshToken(Long.toString(user.getUserSeq()), secretKey);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            Map<String, Object> data = new HashMap<>();
            data.put("token", jwtUtil.createJwt(Long.toString(user.getUserSeq()), secretKey));
            data.put("refresh_token", refreshToken);
            data.put("userSeq", user.getUserSeq());
            data.put("uuid", user.getUuid());

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

    private final String[] adjectives = {"행복한", "재미있는", "똑똑한", "용감한", "지혜로운", "예쁜", "화난", "귀여운", "배고픈", "철학적인", "슬픈", "푸른", "밝은", "아름다운", "신나는", "똑똑한", "시원한", "강한", "이로운", "무명의", "힘내는", "능동한", "영리한", "거친", "신뢰가는", "건물주", "프로", "네모난", "세모난", "동그란", "각진", "반듯한", "날씬한", "순박한", "열정있는", "열심인", "완벽한", "위대한", "엄한", "근엄한", "진지한", "반짝거리는", "모자쓴", "기다란", "계획적인", "매운", "순둥한", "자신있는", "날쌘", "참지않는", "매력적인", "발전한"};
    private final String[] animals = {"강아지", "고양이", "사자", "코끼리", "펭귄", "호랑이", "팬더", "기린", "물고기", "붉은 여우", "여우", "늑대", "양", "뱀", "토끼", "원숭이", "곰", "북극곰", "기러기", "다람쥐", "거북", "해마", "카멜레온", "물범", "불꽃범", "물개", "파이어 폭스", "장미버섯", "바다마코티", "개미", "개미핥기", "장수말벌", "여왕벌", "토종벌", "푸들", "치와와", "말티즈", "비숑", "산토끼", "딱다구리", "부엉이", "청설모", "유니콘", "전기뱀장어", "풍뎅이", "비단뱀"};

    public String generateRandomNickname(){
        Random random = new Random();
        String adjective = adjectives[random.nextInt(adjectives.length)];
        String animal = animals[random.nextInt(animals.length)];

        return adjective + " " + animal;
    }
}
