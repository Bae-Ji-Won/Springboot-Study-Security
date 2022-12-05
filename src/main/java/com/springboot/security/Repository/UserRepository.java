package com.springboot.security.Repository;

import com.springboot.security.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName); // 아이디 중복 제거를 위해 DB에서 유저이름(ID)를 통해 데이터를 가져옴
}
