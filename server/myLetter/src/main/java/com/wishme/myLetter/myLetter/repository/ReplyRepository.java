package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.domain.Reply;
import com.wishme.myLetter.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    long countByToUser(User toUser);

    List<Reply> findAllByToUser(User toUser, Pageable pageable);
}
