package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<MyLetter, Long> {

    // 받는 사람으로 개인편지 조회
    List<MyLetter> findByToUser(User user);

}
