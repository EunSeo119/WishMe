package com.wishme.schoolLetter.schoolLetter.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_name", unique = true, nullable = false)
    private Integer schoolSeq;

    @Column(nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String region;

}
