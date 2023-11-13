package com.wishme.schoolLetter.schoolLetter.service;


import com.wishme.schoolLetter.asset.domain.Asset;
import com.wishme.schoolLetter.asset.repository.AssetRepository;
import com.wishme.schoolLetter.config.AES256;
import com.wishme.schoolLetter.school.domian.School;
import com.wishme.schoolLetter.school.repository.SchoolRepository;
import com.wishme.schoolLetter.schoolLetter.domain.SchoolLetter;
import com.wishme.schoolLetter.schoolLetter.dto.request.SchoolLetterWriteByUuidRequestDto;
import com.wishme.schoolLetter.schoolLetter.dto.request.SchoolLetterWriteRequestDto;
import com.wishme.schoolLetter.schoolLetter.dto.response.SchoolLetterBoardListResponseDto;
import com.wishme.schoolLetter.schoolLetter.dto.response.SchoolLetterDetailResponseDto;
import com.wishme.schoolLetter.schoolLetter.dto.response.SchoolLetterListByPageResponseDto;
import com.wishme.schoolLetter.schoolLetter.repository.SchoolLetterRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchoolLetterService {

    @Value("${key.Base64_Public_Key}")
    String publicKeyBase;

    @Value("${key.Base64_Private_Key}")
    String privateKeyBase;

    @Value("${key.AES256_Key}")
    String key;

    private final SchoolLetterRepository schoolLetterRepository;
    private final AssetRepository assetRepository;
    private final SchoolRepository schoolRepository;


    public SchoolLetterDetailResponseDto getSchoolLetter(Long schoolLetterId) throws Exception {

        SchoolLetter sc = schoolLetterRepository.findBySchoolLetterSeq(schoolLetterId)
                .orElseThrow(()-> new EmptyResultDataAccessException(1));

        AES256 aes256 = new AES256(key);
        String decryptContent = aes256.decrypt(sc.getContent());

        return SchoolLetterDetailResponseDto.builder()
                .schoolLetterSeq(sc.getSchoolLetterSeq())
                .content(decryptContent)
                .schoolName(sc.getSchool().getSchoolName())
                .nickname(sc.getNickname())
                .createAt(sc.getCreateAt())
                .build();
    }


    public List<Asset> getAssetList() {
        char type = 'S';
        return  assetRepository.findByType(type);
    }

    public SchoolLetterListByPageResponseDto getSchoolLetterListBuSchoolUUID(String schoolUUID, Integer page) {

        Pageable pageable = PageRequest.of(page-1, 12, Sort.by(Sort.Order.desc("createAt")));

//        Page<SchoolLetter> schoolLetterPage = schoolLetterRepository.findSchoolLettersBySchoolUuid(schoolUUID, pageable);
        Page<SchoolLetter> schoolLetterPage = schoolLetterRepository.findBySchoolUuidAndIsReport(schoolUUID, false, pageable);

        List<SchoolLetter> schoolLetterList =  schoolLetterPage.getContent();
        System.out.println(schoolLetterList.size());
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
        }else{
            throw new EmptyResultDataAccessException(1);
        }

        School school = schoolRepository.findByUuid(schoolUUID)
                .orElseThrow(()-> new EmptyResultDataAccessException(1));

        return SchoolLetterListByPageResponseDto.builder()
                .schoolLetterList(schoolLetterResponseDtoList)
                .schoolName(school.getSchoolName())
                .totalCount(schoolLetterPage.getTotalElements())
                .totalPage(schoolLetterPage.getTotalPages())
                .schoolId(school.getSchoolSeq())
                .build();
    }

    @Transactional
    public Long writeSchoolLetterByUuid(SchoolLetterWriteByUuidRequestDto writeDto) throws Exception {

        School school = schoolRepository.findByUuid(writeDto.getUuid())
                .orElseThrow(()-> new EmptyResultDataAccessException(1));


        Asset asset = assetRepository.findByAssetSeq(writeDto.getAssetSeq())
                .orElseThrow(()-> new EmptyResultDataAccessException(1));

        AES256 aes256 = new AES256(key);
        String cipherContent = aes256.encrypt(writeDto.getContent());

        SchoolLetter schoolLetter = new SchoolLetter(cipherContent, writeDto.getNickname(), school, asset);
        schoolLetter = schoolLetterRepository.save(schoolLetter);

        return schoolLetter.getSchoolLetterSeq();
    }

    @Transactional
    public Long reportLetter(Long letterSeq) {
        SchoolLetter schoolLetter = schoolLetterRepository.findBySchoolLetterSeq(letterSeq)
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 편지는 존재하지 않습니다.", 1));

        schoolLetter.updateReport(true);
        schoolLetterRepository.save(schoolLetter);

        return schoolLetter.getSchoolLetterSeq();
    }

}
