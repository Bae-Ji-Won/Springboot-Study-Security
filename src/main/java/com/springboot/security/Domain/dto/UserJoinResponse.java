package com.springboot.security.Domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter

// 사용자에게 데이터를 반환하기 위한 DTO
public class UserJoinResponse {
    private String userName;
    private String email;
}
