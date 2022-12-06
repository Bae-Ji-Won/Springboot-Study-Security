package com.springboot.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Security를 사용할때 기본 설정파일
// 이전에는 WebSecurityConfigurerAdapter를 상속받아 사용했지만 이제는 Bean폴더를 만들어서 사용함

@EnableWebSecurity  // 스프링 시큐리티를 활성화하는 어노테이션
@Configuration      // 스프링의 기본 설정 정보들의 환경 세팅을 돕는 어노테이션
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/api/v1/users/join", "/api/v1/users/login").permitAll() // join, login은 언제나 가능
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt사용하는 경우 씀
                .and()
             //   .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                //UserNamePasswordAuthenticationFilter적용하기 전에 JWTTokenFilter를 적용 하라는 뜻 입니다.
                .build();
    }
}
