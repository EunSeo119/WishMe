package com.wishme.user.myLetter.domain;

import com.wishme.user.asset.domain.Asset;
import com.wishme.user.common.domain.BaseTimeEntity;
import com.wishme.user.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "myletter")
public class MyLetter extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_letter_seq", unique = true)
    private Long myLetterSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user", nullable = false)
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_seq", nullable = false)
    private Asset asset;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(name = "from_user_nickname", nullable = false)
    private String fromUserNickname;

    @Column(name = "from_user")
    private Long fromUser;

    @Column(name = "is_public", nullable = false, columnDefinition = "TINYINT(1) default 1")
    private Boolean isPublic;

    @OneToOne(mappedBy = "myLetter")
    private Reply reply;

    @Builder
    public MyLetter(Long myLetterSeq, User toUser, Asset asset, String content, String fromUserNickname, Long fromUser, Boolean isPublic, Reply reply) {
        this.myLetterSeq = myLetterSeq;
        this.toUser = toUser;
        this.asset = asset;
        this.content = content;
        this.fromUserNickname = fromUserNickname;
        this.fromUser = fromUser;
        this.isPublic = isPublic;
        this.reply = reply;
    }
}
