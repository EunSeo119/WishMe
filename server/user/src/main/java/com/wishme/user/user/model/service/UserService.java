package com.wishme.user.user.model.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    ResponseEntity<?> modifyUserInfo(Map<String, String> request, Long userSeq);
    ResponseEntity<?> registerSchool(Map<String, String> request, Long userSeq);
    ResponseEntity<?> searchSchool(String schoolName);
    ResponseEntity<?> getUserInfo(Long userSeq);
}
