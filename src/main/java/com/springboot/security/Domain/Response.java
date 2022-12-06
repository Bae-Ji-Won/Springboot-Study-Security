package com.springboot.security.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
// 최종 결과를 Response에 담아 코드의 상태를 함께 출력해준다.(성공인지 , 오류인지) => 구조{성공유무, 데이터 :{ } }
// 그래야 유저도 어디에서 오류가 발생한지 알 수 있으니
// 모든 Response는 현재 Response객체로 감싸서 Return 한다.
public class Response<T> {
    private String resultCode;
    private T result;


    //  Service에서 데이터가 중복이 되었다면 오류 처리를 하는데
    public static Response<Void> error(String resultCode){
        return new Response(resultCode,null);
    }

    // Controller에서 반환할때 한번 감싸서 반환해줌(성공했다는 메시지와 result 결과 데이터와 같이)
    public static <T> Response<T> success(T result){
        return new Response("Success",result);      // resultCode에는 성공이라는 메시지와 result라는 결과 데이터를 반환함
    }
}
