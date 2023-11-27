package com.wishme.user.myLetter.domain;

import com.wishme.user.common.domain.BaseTimeEntity;
import com.wishme.user.myLetter.domain.MyLetter;
import com.wishme.user.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "reply")
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_seq", unique = true)
    private Long replySeq;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_seq", nullable = false)
    private MyLetter myLetter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user", nullable = false)
    private User toUser;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(name = "from_user_nickname", nullable = false)
    private String fromUserNickname;
}
