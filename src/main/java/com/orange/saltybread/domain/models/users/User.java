package com.orange.saltybread.domain.models.users;

import com.orange.saltybread.domain.models.base.AbstractEntity;
import jakarta.persistence.Entity;

import lombok.Getter;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Entity
public class User extends AbstractEntity {
    private final Pbkdf2PasswordEncoder encoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    private String email;
    private String password;
    private String name;
    private String resetPasswordToken;
    private LocalDateTime lastLogin;

    public User(String email, String password, String name){
        super();
        this.email = email;
        this.password = this.encoder.encode(password);
        this.name = name;
    }

}
//사용자
//- 이메일 주소
//- 닉네임
//- 비밀번호 (해쉬된)
//- 비밀번호 재설정 토큰
//- 마지막 접속 시각
//
//+ 회원가입
//+ 로그인
//+ 로그아웃
//+ 회원정보 수정
//+ 회원 탈퇴
//+ 비밀번호 변경
//+ 비밀번호 분실 설정
//+ 비밀번호 재설정
//+ 이메일 변경 (이메일 본인인증 동반)