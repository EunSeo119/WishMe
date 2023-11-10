package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.myLetter.domain.Reply;
import com.wishme.myLetter.user.domain.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepositoryCustom{

    private final EntityManager em;


    @Override
    public Optional<Reply> findByLetter(MyLetter myLetter){
        String jpql = "SELECT r FROM Reply r WHERE r.myLetter = :myLetter";

        TypedQuery<Reply> query = em.createQuery(jpql, Reply.class)
                .setParameter("myLetter", myLetter);

        return query.getResultList().stream().findFirst();
    }
}
