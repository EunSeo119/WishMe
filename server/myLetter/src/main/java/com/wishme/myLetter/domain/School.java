package com.wishme.myLetter.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_seq", unique = true)
    private Integer schoolSeq;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String region;

    @OneToMany(mappedBy = "school")
    private List<SchoolLetter> schoolLetters = new ArrayList<>();

    @Builder
    public School(Integer schoolSeq, String schoolName, String region) {
        this.schoolSeq = schoolSeq;
        this.schoolName = schoolName;
        this.region = region;
    }
}
