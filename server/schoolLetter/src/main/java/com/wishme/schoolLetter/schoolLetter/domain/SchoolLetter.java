package com.wishme.schoolLetter.schoolLetter.domain;


import com.wishme.schoolLetter.asset.domain.domain.Asset;
import lombok.AccessLevel;
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
    @JoinColumn(name = "school_name")
    private School school;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_seq")
    private Asset assetSeq;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "create_at")
    private Date createAt;
}
