package com.wishme.schoolLetter.school.service;

import com.wishme.schoolLetter.school.domian.School;
import com.wishme.schoolLetter.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public List<School> getSchoolByName(String schoolName) {
        List<School> schoolList = schoolRepository.findBySchoolNameContaining(schoolName);
        if(schoolList==null || schoolList.size()==0)
            throw new EmptyResultDataAccessException(1);
        return schoolList;
    }

    @Transactional
    public String setAllocatedSchoolUUID() {
        List<School> schools = schoolRepository.findAll();
        if(schools==null || schools.size()==0)
            throw new EmptyResultDataAccessException(1);
        for (School school : schools) {
            String uuid = UUID.randomUUID().toString();
            school.setUuid(uuid);
        }
        return schools.get(0).getUuid();
    }


}
