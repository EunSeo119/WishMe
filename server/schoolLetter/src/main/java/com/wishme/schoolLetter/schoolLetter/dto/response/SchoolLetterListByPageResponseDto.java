package com.wishme.schoolLetter.schoolLetter.dto.response;

import com.wishme.schoolLetter.schoolLetter.domain.SchoolLetter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SchoolLetterListByPageResponseDto {

    List<SchoolLetterBoardListResponseDto> schoolLetterList;
    String schoolName;
    Long totalCount;
    Integer totalPage;
    Integer schoolId;

    @Builder
    public SchoolLetterListByPageResponseDto(List<SchoolLetterBoardListResponseDto> schoolLetterList, String schoolName, Long totalCount, Integer totalPage, Integer schoolId) {
        this.schoolLetterList = schoolLetterList;
        this.schoolName = schoolName;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.schoolId = schoolId;
    }

}
