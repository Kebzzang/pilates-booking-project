package com.keb.club_pila.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter

@Builder
public class LoginDto { //비번이랑 아이디만 있으면 됨
    private String username;
    private String password;
}
