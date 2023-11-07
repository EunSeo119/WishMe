package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.domain.Reply;
import com.wishme.myLetter.user.domain.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

@RequiredArgsConstructor
public class ReplyRepositoryImpl {

    private final EntityManager em;

    public Reply findByLetterSeq(Long myLetterId){
        String jpql = "SELECT r FROM Reply r WHERE r.letterSeq = :myLetterId";

        return em.createQuery(jpql, Reply.class)
                .setParameter("myLetterId", myLetterId)
                .getSingleResult();
    }

}
