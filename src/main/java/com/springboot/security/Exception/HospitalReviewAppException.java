package com.springboot.security.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 내가 지정한 클래스내에서만 오류처리하기
@Getter
@AllArgsConstructor
public class HospitalReviewAppException extends RuntimeException{
    private ErrorCode errorCode;    //  Service로부터 생성자를 통해 ErrorCode.DUPLICATED_USER_NAME 저장됨
    private String message;         // Service로부터 생성자를 통해 String.format("Username :"+request.getUserName()) 저장됨

    @Override
    public String toString() {
        if(message == null) {
            //  Service로부터 생성자를 통해 상태만 받아오고 message를 안받아오면 ErrorCode에 미리 설정해둔 Message를 출력한다.
            return errorCode.getMessage();
        }

        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
