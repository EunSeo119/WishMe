package com.wishme.bichnali.asset.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "personalLetter")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_seq", unique = true, nullable = false)
    private Long assetSeq;

    @Column(name = "asset_img", unique = true, nullable = false)
    private String assetImg;
}
