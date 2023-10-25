package com.wishme.myLetter.user.repository;

import com.wishme.myLetter.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

=======

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserSeq(Long assetSeq);

    Optional<User> findByEmail(String email);
>>>>>>> 208bf6878666c67370ddcf1b3376a0a7401fd005
}
