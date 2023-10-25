package com.wishme.myLetter.user.repository;

import com.wishme.myLetter.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserSeq(Long assetSeq);

    Optional<User> findByEmail(String email);

    Optional<User> findByUuid(String uuid);
}
