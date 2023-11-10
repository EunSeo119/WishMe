package com.wishme.schoolLetter.school.repository;

import com.wishme.schoolLetter.school.domian.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Integer> {

    Optional<School> findBySchoolSeq(Integer schoolSeq);

    List<School> findBySchoolNameContaining(String keyword);

    Optional<School> findByUuid(String schoolUuid);
}
