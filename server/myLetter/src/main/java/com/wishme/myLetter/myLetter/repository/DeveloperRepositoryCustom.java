package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeveloperRepositoryCustom {

    // 개발자 전체 편지 조회 - 페이지네이션
    Page<MyLetter> findAllDeveloperLetter(Pageable pageable, User user);

}
