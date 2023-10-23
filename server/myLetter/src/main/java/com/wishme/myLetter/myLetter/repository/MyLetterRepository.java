package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.myLetter.domain.MyLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyLetterRepository extends JpaRepository<MyLetter, Long> {
}
