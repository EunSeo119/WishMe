package com.wishme.schoolLetter.schoolLetter.service;


import com.wishme.schoolLetter.asset.domain.Asset;
import com.wishme.schoolLetter.asset.repository.AssetRepository;
import com.wishme.schoolLetter.school.domian.School;
import com.wishme.schoolLetter.school.repository.SchoolRepository;
import com.wishme.schoolLetter.schoolLetter.domain.SchoolLetter;
import com.wishme.schoolLetter.schoolLetter.dto.request.SchoolLetterWriteRequestDto;
import com.wishme.schoolLetter.schoolLetter.dto.response.SchoolLetterBoardListResponseDto;
import com.wishme.schoolLetter.schoolLetter.dto.response.SchoolLetterDetailResponseDto;
import com.wishme.schoolLetter.schoolLetter.repository.SchoolLetterRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchoolLetterService {

    private final SchoolLetterRepository schoolLetterRepository;
    private final AssetRepository assetRepository;
    private final SchoolRepository schoolRepository;
    public List<SchoolLetterBoardListResponseDto> getSchoolLetterList(Integer schoolId, Integer page) {

        int pageSize = 12; // 한 페이지당 12개의 학교편지
        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc("createAt")));
        Page<SchoolLetter> schoolLetterPage = schoolLetterRepository.findSchoolLettersBySchoolSeq(schoolId, pageable);
        List<SchoolLetter> schoolLetterList =  schoolLetterPage.getContent();
        List<SchoolLetterBoardListResponseDto> schoolLetterResponseDtoList = new ArrayList<>();

        if (schoolLetterList != null && schoolLetterList.size() > 0) {
            for (SchoolLetter schoolLetter : schoolLetterList) {
                schoolLetterResponseDtoList.add(
                        SchoolLetterBoardListResponseDto.builder()
                                .schoolLetterSeq(schoolLetter.getSchoolLetterSeq())
                                .assetSeq(schoolLetter.getAssetSeq().getAssetSeq())
                                .assetImg(schoolLetter.getAssetSeq().getAssetImg())
                                .build()
                );
            }
        }
        return schoolLetterResponseDtoList;
    }


    public SchoolLetterDetailResponseDto getSchoolLetter(Long schoolLetterId) {

        SchoolLetter sc = schoolLetterRepository.findBySchoolLetterSeq(schoolLetterId)
                .orElseThrow(IllegalArgumentException::new);

        return SchoolLetterDetailResponseDto.builder()
                .schoolLetterSeq(sc.getSchoolLetterSeq())
                .content(sc.getContent())
                .schoolName(sc.getSchool().getSchoolName())
                .nickname(sc.getNickname())
                .createAt(sc.getCreateAt())
                .build();
    }


    @Transactional
    public Long writeSchoolLetter(SchoolLetterWriteRequestDto writeDto) {

        School school = schoolRepository.findBySchoolSeq(writeDto.getSchoolSeq())
                .orElseThrow(IllegalArgumentException::new);
        Asset asset = assetRepository.findByAssetSeq(writeDto.getAssetSeq())
                .orElseThrow(IllegalArgumentException::new);

        SchoolLetter schoolLetter = new SchoolLetter(writeDto, school, asset);
        schoolLetter = schoolLetterRepository.save(schoolLetter);

        return schoolLetter.getSchoolLetterSeq();
    }

    public List<Asset> getAssetList() {
        char type = 'S';
        return  assetRepository.findByType(type);
    }

}
