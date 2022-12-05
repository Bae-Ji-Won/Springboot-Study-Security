package com.springboot.security.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// enum = 미리 지정 해놓고 그 값 말고 다른 값들을 넣지 못하게 하여 에측한 범위 내에서만 동작하도록 함
// 예)  1. 요일 선택할때 월~일 사이의 값을 제외한 값을 선택하는 경우
//      2. 룰을 만들어 정의할때 (패스워드에는 대문자가 한개가 꼭 들어가야한다 등)

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // enum을 통해 미리 값을 설정한다.
    // DUPLICATED_USER_NAME의 이름을 가진 에러에 (에러상태 Conflict(409) 출력, 에러메세지)의 값을 넣은 구조만 받는다.
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated.");

    private HttpStatus status;
    private String message;
}
