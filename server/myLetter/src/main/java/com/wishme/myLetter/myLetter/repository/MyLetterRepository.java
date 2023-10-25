package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyLetterRepository extends JpaRepository<MyLetter, Long> {

    List<MyLetter> findAllByToUser(User toUser, Pageable pageable);

    Optional<MyLetter> findByMyLetterSeq(Long myLetterSeq);
}
