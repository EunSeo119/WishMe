package com.wishme.schoolLetter.school.domian;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_seq", unique = true, nullable = false)
    private Integer schoolSeq;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(nullable = false)
    private String region;

    @Column(name = "uuid")
    @Setter
    private String uuid;

    @Builder
    public School(Integer schoolSeq, String schoolName, String region) {
        this.schoolSeq = schoolSeq;
        this.schoolName = schoolName;
        this.region = region;
    }
}
