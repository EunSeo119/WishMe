package com.wishme.myLetter.myLetter.domain;

import com.wishme.myLetter.domain.BaseTimeEntity;
import com.wishme.myLetter.domain.Reply;
import com.wishme.myLetter.asset.domain.Asset;
import com.wishme.myLetter.user.domain.User;
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

    @Column(nullable = false)
    private String nickname;

    @Column(name = "from_user")
    private Long fromUser;

    @Column(name = "is_public", nullable = false, columnDefinition = "TINYINT(1) default 0")
    private Boolean isPublic;

    @OneToOne(mappedBy = "myLetter")
    private Reply reply;

    @Builder
    public MyLetter (User toUser, Asset assetSeq, String content, String nickname, Long fromUser, boolean isPublic, Reply reply){
        this.toUser = toUser;
        this.asset = assetSeq;
        this.content = content;
        this.nickname = nickname;
        this.fromUser = fromUser;
        this.isPublic = isPublic;
        this.reply = reply;
    }

}