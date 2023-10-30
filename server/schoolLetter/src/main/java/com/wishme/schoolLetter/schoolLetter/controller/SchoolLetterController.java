package com.wishme.schoolLetter.schoolLetter.controller;

import com.wishme.schoolLetter.asset.domain.Asset;
import com.wishme.schoolLetter.asset.dto.response.AssetResponseDto;
import com.wishme.schoolLetter.schoolLetter.dto.request.SchoolLetterWriteRequestDto;
import com.wishme.schoolLetter.schoolLetter.dto.response.SchoolLetterBoardListResponseDto;
import com.wishme.schoolLetter.schoolLetter.dto.response.SchoolLetterDetailResponseDto;
import com.wishme.schoolLetter.schoolLetter.service.SchoolLetterService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Getter
@RequestMapping("/api/school/letter")
public class SchoolLetterController {

    private final SchoolLetterService schoolLetterService;

    //칠판 조회
    @GetMapping("/all/{schoolId}/{page}")
    public ResponseEntity<Map<String, Object>> boardList(@PathVariable("schoolId") Integer schoolId,
                                                         @PathVariable("page") Integer page){

        Map<String, Object> resultMap =null;
        HttpStatus status = null;

//        System.out.println("schoolId= "+schoolId);
        resultMap = schoolLetterService.getSchoolLetterList(schoolId,page);
        status = HttpStatus.ACCEPTED;

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    //uuid로 칠판 조회
    @GetMapping("/allByUUID/{schoolUUID}/{page}")
    public ResponseEntity<Map<String, Object>> boardList(@PathVariable("schoolUUID") String schoolUUID,
                                                         @PathVariable("page") Integer page){

        Map<String, Object> resultMap =null;
        HttpStatus status = null;

//        System.out.println("schoolId= "+schoolId);
        resultMap = schoolLetterService.getSchoolLetterListBuSchoolUUID(schoolUUID,page);
        status = HttpStatus.ACCEPTED;

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    //학교 편지 상세 조회
    @GetMapping("/one/{letterId}")
    public ResponseEntity<Map<String, Object>> schoolLetterDetail(@PathVariable("letterId") Long schoolLetterId){

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            SchoolLetterDetailResponseDto schoolLetterDetail = schoolLetterService.getSchoolLetter(schoolLetterId);
            resultMap.put("schoolLetterDetail", schoolLetterDetail);
            status = HttpStatus.ACCEPTED;
        }catch (IllegalArgumentException e){
            resultMap.put("schoolLetterDetail", null);
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    //학교 편지 작성
    @PostMapping("/write")
    public ResponseEntity<Map<String, Object>> writeSchoolLetter(@RequestBody SchoolLetterWriteRequestDto writeDto) {

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            Long schoolLetterId = schoolLetterService.writeSchoolLetter(writeDto);
            if(schoolLetterId == null) throw new IllegalArgumentException();
            status = HttpStatus.ACCEPTED;
        }catch (IllegalArgumentException e){
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    //학교 편지 에셋 목록
    @GetMapping("/assets")
    public ResponseEntity<Map<String, Object>> schoolLetterAssets() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        System.out.println("에셋 목록 확인");
        try {
            List<Asset> schoolAssertList = schoolLetterService.getAssetList();
            resultMap.put("schoolAssertList", schoolAssertList);
            status = HttpStatus.ACCEPTED;

        }catch (IllegalArgumentException e){
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
