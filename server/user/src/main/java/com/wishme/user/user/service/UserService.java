package com.wishme.user.user.service;

import com.wishme.user.user.dto.request.SearchSchoolRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    ResponseEntity<?> modifyUserInfo(Map<String, String> request, Long userSeq);
    ResponseEntity<?> registerSchool(Map<String, String> request, Long userSeq);
    ResponseEntity<?> searchSchool(SearchSchoolRequestDto searchSchoolRequestDto);
    ResponseEntity<?> getUserInfo(Long userSeq);
    Map<String, String> getAccessTokenByRefreshToken(String refreshToken);
}
