package com.wishme.schoolLetter.schoolLetter.domain;

import lombok.AccessLevel;
import lombok.Builder;
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
    @Column(unique = true, nullable = false)
    private Integer schoolSeq;

    @Column( nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String region;

    @Builder
    public School(Integer schoolSeq, String schoolName, String region) {
        this.schoolSeq = schoolSeq;
        this.schoolName = schoolName;
        this.region = region;
    }
}
