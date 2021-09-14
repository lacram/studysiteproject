package com.lacram.studysiteproject.model;

import lombok.*;

import javax.persistence.*;

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

    @Column(nullable = false)
    private String userid;

    @Column(nullable = false)
    private String user_pw;

    @Column(nullable = false)
    private String user_name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Long user_identity;

    @Column()
    private String github_url;

    @Column()
    private String blog_url;

    @Column(nullable = false)
    private int warning_cnt;

    @Column(nullable = false)
    private int account_state;

    public User(String user_id, String user_pw, String user_name, String email, Long user_identity){
        this.userid = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.email = email;
        this.user_identity = user_identity;
        this.warning_cnt = 0;
        this.account_state = 1;
    }

    public User(String user_id, String user_pw, String user_name, String email, Long user_identity,
                String github_url, String blog_url, int warning_cnt, int account_state){
        this.userid = user_id;
        this.user_pw = user_pw;
        this.user_name = user_name;
        this.email = email;
        this.user_identity = user_identity;
        this.github_url = github_url;
        this.blog_url = blog_url;
        this.warning_cnt = warning_cnt;
        this.account_state = account_state;
    }
}
