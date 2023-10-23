package com.wishme.bichnali.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq", unique = true, nullable = false)
    private Long userSeq;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;
}
