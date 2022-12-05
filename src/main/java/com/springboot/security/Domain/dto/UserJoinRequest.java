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
    public User toEntity(){
        return User.builder()
                .userName(this.userName)
                .password(this.password)
                .emailAddress(this.email)
                .build();
    }
}
