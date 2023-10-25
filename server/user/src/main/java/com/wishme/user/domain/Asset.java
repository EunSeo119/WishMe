package com.wishme.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "asset")
    private List<MyLetter> myLetters = new ArrayList<>();

    @OneToMany(mappedBy = "asset")
    private List<SchoolLetter> schoolLetters = new ArrayList<>();
}
