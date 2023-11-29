package com.wishme.user.user.service;

import com.wishme.user.school.domain.School;
import com.wishme.user.user.domain.User;
import com.wishme.user.school.repository.SchoolRepository;
import com.wishme.user.user.dto.request.SearchSchoolRequestDto;
import com.wishme.user.user.dto.response.SearchSchoolResponseDto;
import com.wishme.user.user.repository.UserRepository;
import com.wishme.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    @Value("${key.AES256_Key}")
    private String key;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public ResponseEntity<?> modifyUserInfo(Map<String, String> request, Long userSeq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            User user = userRepository.findByUserSeq(userSeq)
                    .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));
            String newUserNickname = request.get("userNickname");
            String userSchoolSeq = request.get("userSchoolSeq");

            user.setUserNickname(newUserNickname);

            if (Integer.parseInt(userSchoolSeq) != 0) {
                user.setUserSchoolSeq(Integer.parseInt(userSchoolSeq));
            }

            resultMap.put("message", "유저정보 변경 성공");
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("error", e.getMessage());
            resultMap.put("message", "유저정보 변경 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    public ResponseEntity<?> registerSchool(Map<String, String> request, Long userSeq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
            User user = userRepository.findByUserSeq(userSeq)
                    .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));
            String userSchoolSeq = request.get("userSchoolSeq");

            user.setUserSchoolSeq(Integer.parseInt(userSchoolSeq));
            
            resultMap.put("message", "학교 등록 성공");
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("error", e.getMessage());
            resultMap.put("message", "학교 등록 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        return new ResponseEntity<>(resultMap, status);     
    }

    public ResponseEntity<?> searchSchool(SearchSchoolRequestDto searchSchoolRequestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            String schoolName = searchSchoolRequestDto.getSchoolName();
            List<School> schools = schoolRepository.findSchoolsBySchoolNameContaining(schoolName);
            if (schools.size() > 0) {
                List<SearchSchoolResponseDto> searchSchoolResponseDtos = new ArrayList<>();
                for (School school : schools) {
                    SearchSchoolResponseDto searchSchoolResponseDto = SearchSchoolResponseDto.builder()
                            .schoolSeq(school.getSchoolSeq())
                            .schoolName(school.getSchoolName())
                            .region(school.getRegion())
                            .uuid(school.getUuid())
                            .build();
                    searchSchoolResponseDtos.add(searchSchoolResponseDto);
                }
                resultMap.put("data",searchSchoolResponseDtos);
                status = HttpStatus.OK;
            } else {
                throw new IllegalArgumentException("개별자 편지 전체 조회 실패");
            }
        }catch (Exception e) {
            resultMap.put("error", e.getMessage());
            resultMap.put("message", "학교 등록 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    public ResponseEntity<?> getUserInfo(Long userSeq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            User user = userRepository.findByUserSeq(userSeq)
                    .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));

            Map<String, Object> data = new HashMap<>();
            data.put("userNickname", user.getUserNickname());

            if (user.getUserSchoolSeq() == null) {
                data.put("schoolName", "선택 안함");
                data.put("schoolUuid", null);
            } else {
                School school = schoolRepository.findBySchoolSeq(user.getUserSchoolSeq());
                data.put("schoolSeq", user.getUserSchoolSeq());
                data.put("schoolName", school.getSchoolName());
                data.put("schoolUuid", school.getUuid());
            }

            resultMap.put("data", data);
            resultMap.put("message", "유저 정보 조회 성공");
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("message", "유저 정보 조회 실패");
            resultMap.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    public Map<String, String> getAccessTokenByRefreshToken(String refreshToken) {
        Map<String, String> resultMap = new HashMap<>();
//        HttpStatus status = null;

        JwtUtil jwtUtil = new JwtUtil();
        try {
            if (!jwtUtil.isExpired(refreshToken, secretKey)) { // Refresh Token이 만료되지 않은 경우
                User user = userRepository.findByRefreshToken(refreshToken);
                if (user != null) { // Access Token과 Refresh Token 모두 재발급
                    String newAccessToken = jwtUtil.createJwt(Long.toString(user.getUserSeq()), secretKey);
                    String newRefreshToken = jwtUtil.createRefreshToken(Long.toString(user.getUserSeq()), secretKey);

                    resultMap.put("token", newAccessToken);
                    resultMap.put("refresh_token", newRefreshToken);
                }
            }
            resultMap.put("message", "Refresh Token을 통한 Access Token 재발급 성공");
//            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("message", "Access Token 또는 Refresh Token 에러 발생");
            resultMap.put("error", e.getMessage());
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

//        return new ResponseEntity<>(resultMap, status);
        return resultMap;
    }

    @Transactional
    public void noEmail(Long userSeq) {
        User user = userRepository.findByUserSeq(userSeq)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 유저는 존재하지 않습니다.", 1));
        user.updateNoEmail();
    }

}
