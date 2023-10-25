package com.wishme.myLetter.user.domain;

import com.wishme.myLetter.myLetter.domain.MyLetter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
