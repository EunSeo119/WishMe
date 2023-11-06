package com.wishme.user.user.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public interface KakaoService {
    ResponseEntity<?> login(String code, HttpServletResponse response) throws Exception;
}
