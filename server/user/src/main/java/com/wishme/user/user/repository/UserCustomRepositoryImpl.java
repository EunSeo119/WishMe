package com.wishme.user.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wishme.user.myLetter.domain.QMyLetter;
import com.wishme.user.myLetter.domain.QReply;
import com.wishme.user.user.domain.QUser;

import javax.persistence.EntityManager;
import java.util.List;

public class UserCustomRepositoryImpl implements UserCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    QUser user = QUser.user;
    QMyLetter myLetter = QMyLetter.myLetter;
    QReply reply = QReply.reply;

    public UserCustomRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<String> findEmailsByUserAndMyletter() {
        return jpaQueryFactory
                .select(user.email)
                .from(user)
                .innerJoin(myLetter)
                .on(user.userSeq.eq(myLetter.toUser.userSeq))
                .where(user.noMail.eq(false))
                .groupBy(user.email, user.userSeq)
                .fetch();
    }

    @Override
    public List<String> findEmailsByUserAndReply() {
        return jpaQueryFactory
                .select(user.email)
                .from(user)
                .innerJoin(reply)
                .on(user.userSeq.eq(reply.toUser.userSeq))
                .where(reply.isRead.eq(false))
                .groupBy(user.email, user.userSeq)
                .fetch();
    }
}
