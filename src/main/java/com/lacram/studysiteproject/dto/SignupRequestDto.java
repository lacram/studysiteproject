package com.lacram.studysiteproject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String user_id;
    private String user_pw;
    private String user_name;
    private String email;
    private Long user_identity;
}
