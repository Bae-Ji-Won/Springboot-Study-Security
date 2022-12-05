package com.springboot.security.Service;

import com.springboot.security.Domain.Response;
import com.springboot.security.Domain.User;
import com.springboot.security.Domain.dto.UserDto;
import com.springboot.security.Domain.dto.UserJoinRequest;
import com.springboot.security.Domain.dto.UserJoinResponse;
import com.springboot.security.Exception.ErrorCode;
import com.springboot.security.Exception.HospitalReviewAppException;
import com.springboot.security.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 데이터가 없을경우 정상동작, 데이터가 이미 있을겨우 오류 발생(회원가입 불가)
    // 유저에게 입력받은 데이터 중복 검사 및 DB 저장
    public UserDto join(UserJoinRequest request){
        userRepository.findByUserName(request.getUserName())
    // 1. RuntimeException 에러타임 보내기(에러 설정클래스에서 RuntimeException에 해당하는 메서드 실행됨
    //            .ifPresent(user -> new RuntimeException("해당 UserName이 중복 됩니다"));   // 데이터가 있을경우 예외처리(콘솔에만 출력됨)
    //            .orElseThrow(() -> new RuntimeException("해당 UserName이 중복 됩니다"));  // 데이터가 없을경우 예외처리

    // 2. 내가 원하는 에러코드를 만들어서 설정하기
    // enum클래스를 통해 미리 설정해둔 에러구조를 통해 에러를 넘겨준다.
                .ifPresent(user -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME,String.format("Username :"+request.getUserName()));
                });

        User saveUser = userRepository.save(request.toEntity());    // UserJoinRequest -> User Entity변환후 데이터 DB 저장


        return UserDto.fromEntity(saveUser);    // User에게 입력받아 회원가입한 데이터를 UserDto에 저장함
    }
}
