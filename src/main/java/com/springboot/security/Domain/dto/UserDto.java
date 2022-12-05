package com.springboot.security.Domain.dto;

import com.springboot.security.Domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder

// 코드상에서 메서드별 데이터를 주고 받을때 사용하기 위한 DTO
public class UserDto {
    private Long id;
    private String userName;
    private String password;
    private String email;

    // Entity의 값을 UserDto에 저장함
    public static UserDto fromEntity(User user){
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmailAddress())
                .build();

        return userDto;
    }
}
