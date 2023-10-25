package com.wishme.schoolLetter.schoolLetter.domain;


import com.wishme.schoolLetter.asset.domain.Asset;
import com.wishme.schoolLetter.school.domian.School;
import com.wishme.schoolLetter.schoolLetter.dto.request.SchoolLetterWriteRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "SchoolLetter")
public class SchoolLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_letter_seq", unique = true, nullable = false)
    private Long schoolLetterSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_seq")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_seq")
    private Asset assetSeq;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Builder
    public SchoolLetter(Long schoolLetterSeq, School school, Asset assetSeq, String content, String nickname, Date createAt) {
        this.schoolLetterSeq = schoolLetterSeq;
        this.school = school;
        this.assetSeq = assetSeq;
        this.content = content;
        this.nickname = nickname;
        this.createAt = createAt;
    }

    public SchoolLetter(SchoolLetterWriteRequestDto writeDto, School school, Asset asset) {
        this.school = school;
        this.assetSeq = asset;
        this.content = writeDto.getContent();
        this.nickname = writeDto.getNickname();
    }

}
