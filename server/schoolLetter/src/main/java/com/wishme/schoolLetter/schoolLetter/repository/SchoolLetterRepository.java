package com.wishme.schoolLetter.schoolLetter.repository;

import com.wishme.schoolLetter.schoolLetter.domain.SchoolLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SchoolLetterRepository extends JpaRepository<SchoolLetter, Integer> {

    //학교편지 아이디로 찾기
    Optional<SchoolLetter> findBySchoolLetterSeq(Long schoolLetterSeq);

    //학교로 편지 리스트 조회
    @Query("SELECT sl FROM SchoolLetter sl WHERE sl.school.schoolSeq = :schoolSeq ORDER BY sl.createAt DESC")
    Page<SchoolLetter> findSchoolLettersBySchoolSeq(@Param("schoolSeq") Integer schoolSeq, Pageable pageable);

 }
