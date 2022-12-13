package com.springboot.security.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 내가 지정한 클래스내에서만 오류처리하기
@Getter
@AllArgsConstructor
public class AppException extends RuntimeException{
    private ErrorCode errorCode;    //  Service로부터 생성자를 통해 ErrorCode.DUPLICATED_USER_NAME 저장됨
    private String message;         // Service로부터 생성자를 통해 String.format("Username :"+request.getUserName()) 저장됨

    // 원래 RuntimeException이 String으로 에러내용을 반환하기 때문에 상속받은 현재 HospitalReviewAppException도 오버라이딩하여 원하는 에러내용을 반환한다.
    // 하지만 ExceptionManager로 리턴해주는 값이 우선순위가 더 높기 때문에 ExceptionManager의 값이 반환된다.
    @Override
    public String toString() {
        if(message == null) {
            //  Service로부터 생성자를 통해 상태만 받아오고 message를 안받아오면 ErrorCode에 미리 설정해둔 Message를 출력한다.
            return errorCode.getMessage();
        }

        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
