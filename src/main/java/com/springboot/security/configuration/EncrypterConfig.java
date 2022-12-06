package com.springboot.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 비밀번호 암호화 하는 설정파일
@Configuration
public class EncrypterConfig {

    // 원래 자바면 사용하고 싶은 클래스에  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 를 추가하여
    // 객체를 생성하여 사용했을 것이다. 하지만 Spring에서는 new를 통한 객체생성을 하는 대신 Bean폴더를 통해 Spring에서 직접 관리하도록 하는것이다.
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();     // password를 인코딩 해줄때 쓰기 위함
    }


}
