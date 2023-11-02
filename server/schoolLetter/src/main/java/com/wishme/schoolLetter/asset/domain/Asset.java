package com.wishme.schoolLetter.asset.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_seq", unique = true)
    private Long assetSeq;

    @Column(name = "asset_img", nullable = false)
    private String assetImg;

    @Column(name = "type", nullable = false)
    private char type;

//    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL)
//    private List<MyLetter> myLetters = new ArrayList<>();
//
//    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL)
//    private List<SchoolLetter> schoolLetters = new ArrayList<>();
}
