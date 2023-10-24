package com.wishme.user.domain;

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
    @JoinColumn(name = "letter_seq")
    private MyLetter myLetter;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(name = "from_user", nullable = false)
    private String fromUser;
}
