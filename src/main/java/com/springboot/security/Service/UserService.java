package com.springboot.security.Service;

import com.springboot.security.Domain.User;
import com.springboot.security.Domain.dto.UserDto;
import com.springboot.security.Domain.dto.UserJoinRequest;
import com.springboot.security.Exception.ErrorCode;
import com.springboot.security.Exception.HospitalReviewAppException;
import com.springboot.security.Repository.UserRepository;

import com.springboot.security.configuration.EncrypterConfig;
import com.springboot.security.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    // Spring이 자동으로 Bean폴더를 DI를 해줌
    //  EncrypterConfig encoder = new EncrypterConfig().encodePwd(); 와 같은 의미
    private final EncrypterConfig encrypterConfig;

    // 위에 처럼 내가 설정한 Config파일을 호출하여 사용해도 되지만  Spring에서는 기존 BCryptPasswordEncoder 클래스를
    // DI를 하겠다 선언하면 알아서 해당 설정 Bean파일인 EncrypterConfig과 매칭을 시켜서 사용할 수 있게 해준다.
    private final BCryptPasswordEncoder encoder;

    // jwt 토큰에서 토큰 이름을 숨겨두고 해당 이름을 호출한다.(코드상에 토큰 이름이 있으면 절대 안됨, 바로 해킹당함)
    // application.yml 파일에 토큰 가짜 이름을 넣는다(실제 값은 environment variables에 넣는다)
    @Value("${jwt.token.secret}")
    private String secretkey;   // application.yml에서 설정한 token 키의 값을 저장함
    private long expireTimeMs = 1000*60*60; // 토큰 1시간

    // 회원가입 기능
    public UserDto join(UserJoinRequest request){
    // 데이터가 없을경우 정상동작, 데이터가 이미 있을겨우 오류 발생(회원가입 불가)
    // 유저에게 입력받은 데이터 중복 검사 및 DB 저장


        userRepository.findByUserName(request.getUserName())
    // 1. RuntimeException 에러타임 보내기(에러 설정클래스에서 RuntimeException에 해당하는 메서드 실행됨
    //            .ifPresent(user -> new RuntimeException("해당 UserName이 중복 됩니다"));   // 데이터가 있을경우 예외처리(콘솔에만 출력됨)

    // 2. 내가 원하는 에러코드를 만들어서 설정하기
    // enum클래스를 통해 미리 설정해둔 에러구조를 통해 에러를 넘겨준다.
                .ifPresent(user -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME,String.format("Username :"+request.getUserName()));
                });


     // 비밀번호 암호화 하는방식 2가지
     //  1. 내가 설정한 Config파일인 EncrypterConfig를 DI를받아 사용하는 법
     //     EncrypterConfig의 메서드인 encodePwd()를 호출하고 BCryptPasswordEncoder안에 있는 encode() 기능 사용
        User saveUser = userRepository.save(request.toEntity(encrypterConfig.encodePwd().encode(request.getPassword())));

     // 2. 기존 클래스인 BCryptPasswordEncoder를 DI를 받아 사용하는 법
     //     BCryptPasswordEncoder클래스안에 있는 메서드 encode() 기능 사용 => 자동으로 EncrypterConfig Bean과 연결됨
        User saveUser2 = userRepository.save(request.toEntity(encoder.encode(request.getPassword())));    // UserJoinRequest -> User Entity변환후 데이터 DB 저장 , password는 암호화 하여 저장
        
        return UserDto.fromEntity(saveUser2);    // User에게 입력받아 회원가입한 데이터를 UserDto에 저장함
    }




    // 로그인 기능
    public String login(String userName, String password) {
        // 유저이름(ID)이 있는지 확인
        // 없다면 Not Found 에러 발생
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()-> new HospitalReviewAppException(ErrorCode.NOT_FOUND,String.format("%s는 가입된 적이 없습니다.",userName)));

        // password일치 하는지 여부 확인
        if(!encoder.matches(password,user.getPassword())){      // encoder.matches는 암호화된 문자를 입력된 문자와 비교해주는 메서드이다
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD,String.format("비밀번호가 틀립니다."));
        }

        // 두가지 확인 중 에외가 없다면 token 발행
        return JwtTokenUtil.createToken(userName,expireTimeMs,secretkey);
    }
}
