package com.wishme.user.user.model.service;

import com.wishme.user.domain.User;
import com.wishme.user.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> modifyNickname(Map<String, String> request, Long userSeq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            User user = userRepository.findByUserSeq(userSeq);
            String newUserNickname = request.get("userNickname");

            user.setUserNickname(newUserNickname);

            resultMap.put("message", "닉네임 변경 성공");
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("error", e.getMessage());
            resultMap.put("message", "닉네임 변경 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @Override
    public ResponseEntity<?> registerSchool(Map<String, String> request, Long userSeq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
            User user = userRepository.findByUserSeq(userSeq);
            
            resultMap.put("message", "학교 등록 성공");
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("error", e.getMessage());
            resultMap.put("message", "학교 등록 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        return new ResponseEntity<>(resultMap, status);     
    }

    @Override
    public ResponseEntity<?> modifySchool(Map<String, String> request, Long userSeq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
            User user = userRepository.findByUserSeq(userSeq);
            
            resultMap.put("message", "학교 수정 성공");
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("error", e.getMessage());
            resultMap.put("message", "학교 수정 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        return new ResponseEntity<>(resultMap, status); 
    }

    @Override
    public ResponseEntity<?> searchSchool(Map<String, String> request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
            
            resultMap.put("message", "학교 검색 성공");
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultMap.put("error", e.getMessage());
            resultMap.put("message", "학교 검색 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        return new ResponseEntity<>(resultMap, status);
    }
}
