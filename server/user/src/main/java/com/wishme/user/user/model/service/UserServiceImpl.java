package com.wishme.user.user.model.service;

import com.wishme.user.domain.School;
import com.wishme.user.domain.User;
import com.wishme.user.school.model.repository.SchoolRepository;
import com.wishme.user.user.model.dto.request.SearchSchoolRequestDto;
import com.wishme.user.user.model.dto.response.SearchSchoolResponseDto;
import com.wishme.user.user.model.repository.UserRepository;
import com.wishme.user.util.AES256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;

    @Value("{key.AES256_Key}")
    private String key;

    @Override
    public ResponseEntity<?> modifyUserInfo(Map<String, String> request, Long userSeq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            User user = userRepository.findByUserSeq(userSeq);
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

    @Override
    public ResponseEntity<?> registerSchool(Map<String, String> request, Long userSeq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        
        try {
            User user = userRepository.findByUserSeq(userSeq);
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

    @Override
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

//        try {
//            String schoolName = searchSchoolRequestDto.getSchoolName();
//            List<School> schools = schoolRepository.findSchoolsBySchoolNameContaining(schoolName);
//
//            List<SearchSchoolResponseDto> searchSchoolResponseDtos = new ArrayList<>();
//            for(School school : schools){
//                SearchSchoolResponseDto searchSchoolResponseDto = SearchSchoolResponseDto.builder()
//                        .schoolSeq(school.getSchoolSeq())
//                        .schoolName(school.getSchoolName())
//                        .region(school.getRegion())
//                        .uuid(school.getUuid())
//                        .build();
//                searchSchoolResponseDtos.add(searchSchoolResponseDto);
//            }
//            return searchSchoolResponseDtos;
////            resultMap.put("schoolSeq", schools);
////            resultMap.put("message", "학교 검색 성공");
////            status = HttpStatus.OK;
//        } catch (Exception e) {
////            resultMap.put("error", e.getMessage());
////            resultMap.put("message", "학교 검색 실패");
////            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }

//        return new ResponseEntity<>(resultMap, status);

    }

    @Override
    public ResponseEntity<?> getUserInfo(Long userSeq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            User user = userRepository.findByUserSeq(userSeq);

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
}
