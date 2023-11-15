package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.user.domain.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@RequiredArgsConstructor
public class DeveloperRepositoryImpl implements DeveloperRepositoryCustom{

    private final EntityManager em;

    public Integer findTotalCnt(User user){
        String jpql = "SELECT m FROM MyLetter m WHERE m.toUser = :user";

        TypedQuery<MyLetter> query = em.createQuery(jpql, MyLetter.class)
                .setParameter("user", user);

        int totalCnt = query.getResultList().size();   // 전체 결과 수

        return totalCnt;
    }
}
