package com.springboot.security.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.security.Domain.User;
import com.springboot.security.Domain.dto.UserDto;
import com.springboot.security.Domain.dto.UserJoinRequest;
import com.springboot.security.Exception.ErrorCode;
import com.springboot.security.Exception.HospitalReviewAppException;
import com.springboot.security.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
    import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    void join_success() throws Exception {
        // given
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("han")
                .password("1q2w3e4r")
                .email("oceanfog1@gmail.com")
                .build();

        User user = userJoinRequest.toEntity();
        UserDto userDto = UserDto.fromEntity(user);

        when(userService.join(any())).thenReturn(userDto);

        // when, then
        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)        // Json 타입으로 사용
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))      // 삽입한 데이터 dto를 json 형식으로 변환
                .andDo(print())
                // userName 존재 여부 확인
                .andExpect(jsonPath("$..userName").exists())
                // userName의 값 비교
                .andExpect(jsonPath("$..userName").value("han"))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("회원가입 실패")
    void join_fail() throws Exception {
        UserJoinRequest userJoinRequest = UserJoinRequest.builder()
                .userName("han")
                .password("1q2w3e4r")
                .email("oceanfog1@gmail.com")
                .build();

        // 이전에는 when/thenReturn을 통해 구현했는데 그렇게 하면 given 구역에서 when을 사용하면 헷갈릴 수 있으므로 given으로 구역을 표시하며 정확히 한다.
        given(userService.join(any()))
                .willThrow(new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userJoinRequest)))
                .andDo(print())
                .andExpect(status().isConflict());

        verify(userService).join(any());
    }
}