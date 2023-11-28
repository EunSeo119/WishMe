package com.wishme.user.user.repository;

import com.wishme.user.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository, QuerydslPredicateExecutor<User> {
    Optional<User> findByUserSeq(Long userSeq);
    Optional<User> findByEmail(String email);
    User findByRefreshToken(String refreshToken);
}
