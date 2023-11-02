package com.wishme.myLetter.myLetter.repository;

import com.wishme.myLetter.myLetter.domain.MyLetter;
import com.wishme.myLetter.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
public class DeveloperRepositoryImpl implements DeveloperRepositoryCustom{

    private final EntityManager em;

    @Override
    public Page<MyLetter> findAllDeveloperLetter(Pageable pageable, User user) {
        String jpql = "SELECT m FROM MyLetter m WHERE m.toUser = :user";

        TypedQuery<MyLetter> query = em.createQuery(jpql, MyLetter.class)
                .setParameter("user", user);

        int totalCnt = query.getResultList().size();   // 전체 결과 수

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<MyLetter> result = query.getResultList();

        return new PageImpl<>(result, pageable, totalCnt);
    }
}
