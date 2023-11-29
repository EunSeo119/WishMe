package com.wishme.myLetter.myLetter.domain;

import com.wishme.myLetter.common.domain.BaseTimeEntity;
import com.wishme.myLetter.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user", nullable = false)
    private User fromUser;

    @Column(name = "letter_color", nullable = false)
    private char color;

    @Column(name = "is_read", nullable = false, columnDefinition = "TINYINT(1) default 0")
    private Boolean isRead;

    @Builder
    public Reply(Long replySeq, MyLetter myLetter, User toUser, String content, String fromUserNickname, char color, User fromUser) {
        this.replySeq = replySeq;
        this.myLetter = myLetter;
        this.toUser = toUser;
        this.content = content;
        this.fromUserNickname = fromUserNickname;
        this.color = color;
        this.fromUser = fromUser;
    }

    public void updateIsRead() {
        this.isRead = true;
    }
}

