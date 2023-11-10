package com.wishme.schoolLetter.schoolLetter.controller;

import com.wishme.schoolLetter.schoolLetter.dto.request.SchoolLetterWriteByUuidRequestDto;
import com.wishme.schoolLetter.schoolLetter.service.SchoolLetterService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Getter
@RequestMapping("/api/school/letter")
public class SchoolLetterController {

    private final SchoolLetterService schoolLetterService;

    @GetMapping("/allByUUID/{schoolUUID}/{page}")
    public ResponseEntity<?> boardList(@PathVariable("schoolUUID") String schoolUUID,
                                       @PathVariable("page") Integer page) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schoolLetterService.getSchoolLetterListBuSchoolUUID(schoolUUID, page));
    }

    @GetMapping("/one/{letterId}")
    public ResponseEntity<?> schoolLetterDetail(@PathVariable("letterId") Long schoolLetterId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schoolLetterService.getSchoolLetter(schoolLetterId));
    }

    @PostMapping("/write/uuid")
    public ResponseEntity<?> writeSchoolLetterByUuid(@RequestBody SchoolLetterWriteByUuidRequestDto writeDto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schoolLetterService.writeSchoolLetterByUuid(writeDto));
    }

    @GetMapping("/assets")
    public ResponseEntity<?> schoolLetterAssets() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(schoolLetterService.getAssetList());
    }

    @PutMapping("/report/{letterSeq}")
    public ResponseEntity<?> reportLetter(@PathVariable("letterSeq") Long letterSeq) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(schoolLetterService.reportLetter(letterSeq));
    }

}
