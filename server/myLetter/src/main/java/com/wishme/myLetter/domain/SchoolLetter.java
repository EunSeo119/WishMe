package com.wishme.myLetter.domain;


import com.wishme.myLetter.asset.domain.Asset;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "SchoolLetter")
public class SchoolLetter extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_letter_seq", unique = true)
    private Long schoolLetterSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_seq", nullable = false)
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_seq", nullable = false)
    private Asset asset;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(nullable = false)
    private String nickname;

    @Builder
    public SchoolLetter(Long schoolLetterSeq, School school, Asset asset, String content, String nickname) {
        this.schoolLetterSeq = schoolLetterSeq;
        this.school = school;
        this.asset = asset;
        this.content = content;
        this.nickname = nickname;
    }

}
