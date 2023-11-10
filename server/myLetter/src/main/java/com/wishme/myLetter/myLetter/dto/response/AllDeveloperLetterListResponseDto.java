package com.wishme.myLetter.myLetter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AllDeveloperLetterListResponseDto {

    // 총 편지 수
    private Long totalLetters;
    // 총 페이지 수
    private int totalPages;
    // 페이지 당 편지
    private List<AllDeveloperLetterResponseDto> lettersPerPage = new ArrayList<>();

}
