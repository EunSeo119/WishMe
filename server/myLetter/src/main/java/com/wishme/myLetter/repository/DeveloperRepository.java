package com.wishme.myLetter.repository;

import com.wishme.myLetter.domain.MyLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<MyLetter, Long> {
}
