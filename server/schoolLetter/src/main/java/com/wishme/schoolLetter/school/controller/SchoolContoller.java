package com.wishme.schoolLetter.school.controller;

import com.wishme.schoolLetter.school.domian.School;
import com.wishme.schoolLetter.school.service.SchoolService;
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
@RequestMapping("/api/school")
public class SchoolContoller {

    private final SchoolService schoolService;

    @PostMapping("/search/all")
    public ResponseEntity<?> writeSchoolLetter(@RequestBody Map<String, String> request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(schoolService.getSchoolByName(request.get("schoolName")));

    }

    @GetMapping("/allocateID")
    public ResponseEntity<Map<String, Object>> allocatedSchoolUUID() {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            String uuid = schoolService.setAllocatedSchoolUUID();
            resultMap.put("uuid",uuid);
            status = HttpStatus.ACCEPTED;
        } catch (IllegalArgumentException e) {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }
    }
