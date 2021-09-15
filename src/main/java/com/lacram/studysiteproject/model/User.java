package com.lacram.studysiteproject.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, name = "user_id")
    private String userid;

    @Column(nullable = false, name = "user_pw")
    private String userpw;

    @Column(nullable = false, name = "user_name")
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, name = "user_identity")
    private Long useridentity;

    @Column(name = "github_url")
    private String githuburl;

    @Column(name = "blog_url")
    private String blogurl;

    @Column(nullable = false, name = "warning_cnt")
    private int warningcnt;

    @Column(nullable = false, name = "account_state")
    private int accountstate;

}
