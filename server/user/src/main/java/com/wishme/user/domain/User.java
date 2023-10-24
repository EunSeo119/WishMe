package com.wishme.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq", unique = true)
    private Long userSeq;

    @Column(nullable = false)
    private String email;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @Column(name = "user_school_seq")
    private Long userSchoolSeq;

    @Column(name = "uuid")
    private String uuid;

    @OneToMany(mappedBy = "toUser")
    private List<MyLetter> myLetters = new ArrayList<>();
}
