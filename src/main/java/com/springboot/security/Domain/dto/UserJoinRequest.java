package com.springboot.security.Domain.dto;

import com.springboot.security.Domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
// 사용자에게 데이터를 입력받기 위한 DTO
public class UserJoinRequest {
    private String userName;
    private String password;
    private String email;

    // 사용자에게 입력받은 데이터를 Entity로 보내줌
    public User toEntity(String password){          // 비밀번호를 암호화 해야하기 때문에 password는 따로 입력받아 저장시킨다.
        return User.builder()
                .userName(this.userName)
                .password(password)
                .emailAddress(this.email)
                .build();
    }
}
