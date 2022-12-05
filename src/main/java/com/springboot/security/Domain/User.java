package com.springboot.security.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)      // userName은 중복값이 있으면 안됨. (회원가입할때 아이디중복이 안되는것)
    private String userName;
    private String password;
    private String emailAddress;
}
