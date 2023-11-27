package com.wishme.user.user.domain;

import com.wishme.user.common.domain.BaseTimeEntity;
import com.wishme.user.myLetter.domain.MyLetter;
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
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq", unique = true)
    private Long userSeq;

    @Column(nullable = false)
    private String email;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @Column(name = "user_school_seq")
    private Integer userSchoolSeq;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "refresh_token")
    private String refreshToken;

    @OneToMany(mappedBy = "toUser")
    private List<MyLetter> myLetters = new ArrayList<>();

    public User(String email, String userNickname, String uuid) {
        this.email = email;
        this.userNickname = userNickname;
        this.uuid = uuid;
    }
}
