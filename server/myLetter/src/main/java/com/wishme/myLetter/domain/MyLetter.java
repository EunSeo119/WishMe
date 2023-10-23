package com.wishme.bichnali.myLetter.domain;

import com.wishme.bichnali.asset.domain.Asset;
import com.wishme.bichnali.common.domain.BaseTimeEntity;
import com.wishme.bichnali.user.domain.User;
import lombok.AccessLevel;
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
    @Column(name = "my_letter_seq", unique = true, nullable = false)
    private Long myLetterSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_seq")
    private Asset assetSeq;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "to_user")
    private Long toUser;

    @Column(name = "is_public", nullable = false, columnDefinition = "TINYINT(1) default 0")
    private Boolean isPublic;

}
