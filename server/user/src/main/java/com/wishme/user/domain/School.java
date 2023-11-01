package com.wishme.user.domain;

import lombok.*;

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

    @Column(name = "uuid")
    private String uuid;

    @Builder
    public School(Integer schoolSeq, String schoolName, String region, String uuid) {
        this.schoolSeq = schoolSeq;
        this.schoolName = schoolName;
        this.region = region;
        this.uuid = uuid;
    }
}
