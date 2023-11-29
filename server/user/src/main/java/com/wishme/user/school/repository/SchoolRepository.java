package com.wishme.user.school.repository;

import com.wishme.user.school.domain.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface SchoolRepository extends JpaRepository<School, Integer> {
    List<School> findSchoolsBySchoolNameContaining(String schoolName);
    School findBySchoolSeq(int schoolSeq);
}
