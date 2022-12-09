package com.springboot.security.Controller;


import com.springboot.security.Domain.Response;
import com.springboot.security.Domain.dto.*;
import com.springboot.security.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 회원가입 기능
    @PostMapping("/join")
    public ResponseEntity<Response<UserJoinResponse>> join(@RequestBody UserJoinRequest userJoinRequest){
        UserDto userDto = userService.join(userJoinRequest);        // 유저가 입력한 데이터 중복검사 및 DB에 저장
        return ResponseEntity.ok().body(Response.success(new UserJoinResponse(userDto.getUserName(),userDto.getEmail())));
    }

    // 로그인 기능
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
        String token = userService.login(userLoginRequest.getUserName(),userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
